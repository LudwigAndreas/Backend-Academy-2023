package ru.shop.backend.search.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shop.backend.search.model.elastic.ItemDocument;
import ru.shop.backend.search.repository.ItemDbRepository;
import ru.shop.backend.search.repository.ItemElasticsearchRepository;

import javax.transaction.Transactional;

/*
* ReindexSearchService - a service that re-indexes data from the database into Elasticsearch
*
* Can be replaced with a cron task if you need to reindex data using spring application
* Can be replaced with logstash if the reindexing logic does not require additional preprocessing
* */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReindexItemsScheduler {

    private final ItemDbRepository dbRepository;
    private final ItemElasticsearchRepository searchRepository;


    /* delay with 12 hours */
    @Scheduled(fixedDelay = 43200000)
//    @Scheduled(cron = "0/1 0 0/12 ? * * *")
//    @Scheduled(cron = "0 0 0,12 ? * * *")
    @Transactional
    public void reindex(){
        log.info("генерация индексов по товарам запущена");
        dbRepository.findAllItems()
                .parallel()
                .map(ItemDocument::new)
                .forEach(searchRepository::save);
        log.info("генерация индексов по товарам закончилась");

    }
}
