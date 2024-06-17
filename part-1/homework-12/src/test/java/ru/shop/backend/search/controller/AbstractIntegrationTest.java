package ru.shop.backend.search.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
@ActiveProfiles("test-containers-flyway")
public abstract class AbstractIntegrationTest {

    private static final String DATABASE_NAME = "site-test";

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.0"))
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withNetwork(Network.SHARED)
            .withExposedPorts(5432)
            .waitingFor(new WaitAllStrategy())
            .withInitScript("integration-test-schema.sql");

    @Container
    static ElasticsearchContainer elasticsearch = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.17.14")
            .withReuse(true)
            .withEnv("discovery.type", "single-node")
            .withEnv("cluster.name", "elasticsearch")
            .withEnv("xpack.security.enabled", "false")
            .withEnv("network.host", "0.0.0.0")
            .withExposedPorts(9200, 9300);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.USERNAME=" + postgres.getUsername(),
                    "CONTAINER.PASSWORD=" + postgres.getPassword(),
                    "CONTAINER.URL=" + postgres.getJdbcUrl(),
                    "CONTAINER.ELASTICSEARCH.URL=" + elasticsearch.getHttpHostAddress()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
