package post.parthmistry.demoweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationProperties {

    private ElasticsearchProperties elasticsearch;

    public ElasticsearchProperties getElasticsearch() {
        return elasticsearch;
    }

    public void setElasticsearch(ElasticsearchProperties elasticsearch) {
        this.elasticsearch = elasticsearch;
    }

}

class ElasticsearchProperties {

    private String hostname;

    private int port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
