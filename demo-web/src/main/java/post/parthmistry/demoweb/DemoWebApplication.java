package post.parthmistry.demoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import post.parthmistry.demoweb.config.ApplicationProperties;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
@EnableConfigurationProperties(ApplicationProperties.class)
public class DemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}

}
