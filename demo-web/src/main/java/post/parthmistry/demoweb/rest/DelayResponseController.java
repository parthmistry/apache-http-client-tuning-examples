package post.parthmistry.demoweb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post.parthmistry.demoweb.service.ActiveRequestCountLogger;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class DelayResponseController {

    @Autowired
    private ActiveRequestCountLogger activeRequestCountLogger;

    @GetMapping("/sample/request")
    public Mono<HashMap<String, Object>> sampleRequest() {
        activeRequestCountLogger.addActiveRequest();
        return Mono.delay(Duration.ofMillis(5000))
                .map(delay -> new HashMap<String, Object>())
                .doFinally(signalType -> activeRequestCountLogger.removeActiveRequest());
    }

}
