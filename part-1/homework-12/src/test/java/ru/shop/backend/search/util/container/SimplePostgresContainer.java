package ru.shop.backend.search.util.container;

import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.utility.DockerImageName;

public class SimplePostgresContainer extends PostgreSQLContainer<SimplePostgresContainer> {
    public SimplePostgresContainer() {
        super(DockerImageName.parse("postgres:14.10"));
        withNetwork(Network.SHARED);
        addFixedExposedPort(5432, 5432);
        waitingFor(new WaitAllStrategy());
        withReuse(true);
        withEnv("PGCLIENTENCODING", "UTF-8");
    }
}
