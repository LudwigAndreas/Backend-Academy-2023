package ru.shop.backend.search.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import  org.springframework.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableElasticsearchRepositories
@EnableScheduling
public class AppConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        return new OpenAPI().info(new Info().version(version));
    }

}
