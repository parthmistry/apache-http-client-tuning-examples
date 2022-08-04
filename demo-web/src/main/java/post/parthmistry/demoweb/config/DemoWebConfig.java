package post.parthmistry.demoweb.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import post.parthmistry.demoweb.service.ActiveRequestCountLogger;

@Configuration
public class DemoWebConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public ActiveRequestCountLogger activeRequestCountLogger() {
        var activeRequestCountLogger = new ActiveRequestCountLogger();
        activeRequestCountLogger.setDaemon(true);
        activeRequestCountLogger.start();
        return activeRequestCountLogger;
    }

    @Bean
    @Qualifier("default")
    public ElasticsearchAsyncClient defaultClient() {
        var elasticsearchProperties = applicationProperties.getElasticsearch();
        var restClient = RestClient.builder(new HttpHost(elasticsearchProperties.getHostname(), elasticsearchProperties.getPort()))
                .build();
        var transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchAsyncClient(transport);
    }

    @Bean
    @Qualifier("tuned")
    public ElasticsearchAsyncClient tunedClient() {
        var elasticsearchProperties = applicationProperties.getElasticsearch();
        var restClient = RestClient.builder(new HttpHost(elasticsearchProperties.getHostname(), elasticsearchProperties.getPort()))
                .setHttpClientConfigCallback(clientConfigCallback -> {
                    return clientConfigCallback.setMaxConnPerRoute(50).setMaxConnTotal(50);
                })
                .build();
        var transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchAsyncClient(transport);
    }

}
