package ru.shop.backend.search.util.container;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticsearchTestContainer extends ElasticsearchContainer {

        private static final String IMAGE_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:7.17.14";
        private static ElasticsearchTestContainer container;

        private ElasticsearchTestContainer() {
            super(IMAGE_VERSION);
        }

        public static ElasticsearchTestContainer getInstance() {
            if (container == null) {
                container = new ElasticsearchTestContainer();
            }
            return container;
        }

        @Override
        public void start() {
            super.start();
            System.setProperty("spring.elasticsearch.rest.uris", container.getHttpHostAddress());
        }

        @Override
        public void stop() {
            //do nothing, JVM handles shut down
        }
}
