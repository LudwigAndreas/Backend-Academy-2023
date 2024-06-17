package ru.tinkoff.seminars.topics.service;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.topics.model.DatabaseSequenceEntity;

import java.util.concurrent.ExecutionException;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SequenceGeneratorService {

    private final ReactiveMongoOperations mongoOperations;

    public SequenceGeneratorService(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long generateSequence(String seqName) throws ExecutionException, InterruptedException {
        return mongoOperations.findAndModify(new Query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequenceEntity.class).toFuture().get().getSeq();
    }
}
