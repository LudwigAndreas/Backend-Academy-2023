package ru.tinkoff.seminars.topics.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;
import ru.tinkoff.seminars.topics.service.SequenceGeneratorService;

import java.util.concurrent.ExecutionException;

@Component
public class TopicModelListener extends AbstractMongoEventListener<TopicEntity> {
    private final Logger logger = LoggerFactory.getLogger(TopicModelListener.class);
    private final SequenceGeneratorService sequenceGeneratorService;

    public TopicModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<TopicEntity> event) {
        try {
            event.getSource().setId(sequenceGeneratorService.generateSequence(TopicEntity.SEQUENCE_NAME));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error during generating topic sequence: {}", e.getMessage());
        }
    }
}
