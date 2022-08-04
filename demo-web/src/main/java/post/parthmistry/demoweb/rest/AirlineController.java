package post.parthmistry.demoweb.rest;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post.parthmistry.demoweb.service.ActiveRequestCountLogger;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AirlineController {

    @Autowired
    @Qualifier("default")
    private ElasticsearchAsyncClient defaultClient;

    @Autowired
    @Qualifier("tuned")
    private ElasticsearchAsyncClient tunedClient;

    @Autowired
    private ActiveRequestCountLogger activeRequestCountLogger;

    @PostMapping("/default/airline")
    public Mono<Map<String, Object>> defaultIndexAirline(@RequestBody Map<String, Object> airlineDataMap) {
        return indexAirlineDocument(defaultClient, airlineDataMap);
    }

    @PostMapping("/tuned/airline")
    public Mono<Map<String, Object>> tunedIndexAirline(@RequestBody Map<String, Object> airlineDataMap) {
        activeRequestCountLogger.addActiveRequest();
        return indexAirlineDocument(tunedClient, airlineDataMap);
    }

    private Mono<Map<String, Object>> indexAirlineDocument(ElasticsearchAsyncClient esClient, Map<String, Object> airlineDataMap) {
        return Mono.<Map<String, Object>>create(monoSink ->
            esClient.index(indexRequestBuilder ->
                indexRequestBuilder.index("airline").document(airlineDataMap).refresh(Refresh.WaitFor)
            ).whenComplete((indexResponse, exception) -> {
                if (exception == null) {
                    monoSink.success(Map.of("id", indexResponse.id()));
                } else {
                    monoSink.error(exception);
                }
            })
        ).doFinally(signalType -> activeRequestCountLogger.removeActiveRequest());
    }

}
