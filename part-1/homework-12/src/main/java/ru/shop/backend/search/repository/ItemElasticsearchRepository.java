package ru.shop.backend.search.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.shop.backend.search.model.elastic.ItemDocument;

import java.util.List;

//TODO: refactor this interface
public interface ItemElasticsearchRepository extends ElasticsearchRepository<ItemDocument, Integer> {


    @Query("{\n" +
            "    \"multi_match\": {\n" +
            "      \"fields\":  [ \"type^2\", \"name^2\", \"description\"],\n" +
            "      \"operator\":   \"AND\",\n" +
            "        \"query\" : \"?0\",\n" +
            "      \"fuzziness\" :1, \n" +
            "        \"boost\": \"1\",\n" +
            "       \"analyzer\" : \"russian\"\n" +
            "      }\n" +
            "    }\n")
    List<ItemDocument> find(String name, Pageable pageable);

    @Query("{\"match\": {\n" +
            "      \"type\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"2\"\n" +
            "      }\n" +
            "    }}")
    List<ItemDocument> findAllByType(String name, Pageable pageable);

    @Query("{\"match\": {\n" +
            "      \"brand\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"1\",\n" +
            "        \"boost\": \"1\"\n" +
            "      }\n" +
            "    }}")
    List<ItemDocument> findAllByBrand(String name, Pageable pageable);

    @Query("{\n" +
            "\"bool\": { \n" +
            "      \"must\": [\n" +
            "        {\n" +
            "        \"multi_match\": {\n" +
            "          \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"1\",\n" +
            "        \"boost\": \"1\",\n" +
            "       \"analyzer\" : \"russian\",\n" +
            "\"operator\":   \"AND\" ,\n" +
            "          \"fields\": [\n" +"\"name^4\", \"description\", \"type\"\n" +
            "          ]\n" +
            "        }\n" +
            "      }    ],\n" +
            "      \n" +
            "      \"filter\":  [{\"match\":{\n" +
            "                    \"brand\": \n" +
            "                        \"?1\"}\n" +
            "                    }]\n" +
            "      \n" +
            "    }\n" +
            "}")
    List<ItemDocument> findAllByBrand(String text, String brand, Pageable pageable);

@Query("{\"match\": {\n" +
        "      \"fulltext\": {\n" +
        "        \"query\": \"?0\",\n" +
        "        \"fuzziness\": \"2\"\n" +
        "      }\n" +
        "    }}")
    List<ItemDocument> findAllNotStrong(String text, Pageable pageable);

    @Query("{\"match\": {\n" +
            "      \"catalogue\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"1\",\n" +
            "        \"boost\": \"1\"\n" +
            "      }\n" +
            "    }}")
    List<ItemDocument> findByCatalogue(String text, Pageable pageable);

    @Query("{\n" +
            "\"bool\": { \n" +
            "      \"must\": [\n" +
            "        {\n" +
            "        \"multi_match\": {\n" +
            "          \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"1\",\n" +
            "        \"boost\": \"1\",\n" +
            "       \"analyzer\" : \"russian\",\n" +
            "\"operator\":   \"AND\" ,\n" +
            "          \"fields\": [\n" +"\"name^4\", \"description\", \"type\"\n" +
            "          ]\n" +
            "        }\n" +
            "      }    ],\n" +
            "      \n" +
            "      \"filter\":  [{\"match\":{\n" +
            "                    \"type\": \n" +
            "                        \"?1\"}\n" +
            "                    }]\n" +
            "    }\n" +
            "}")

    List<ItemDocument> findAllByType(String text, String type, Pageable pageable);
    @Query("{\n" +
            "\"bool\": { \n" +
            "      \"must\": [\n" +
            "        {\n" +
            "        \"multi_match\": {\n" +
            "          \"query\": \"?0\",\n" +
            "\t\t\t\"fuzziness\" :2,\n" +
            "        \"boost\": \"1\",\n" +
            "       \"analyzer\" : \"russian\",\n" +
            "\"operator\":   \"AND\" ,\n" +
            "          \"fields\": [\n" +"\"name^4\", \"description\", \"type\"\n" +
            "          ]\n" +
            "        }\n" +
            "      }    ],\n" +
            "      \n" +
            "      \"filter\":  [{\"match\":{\n" +
            "                    \"brand\": \n" +
            "                        \"?1\"}\n" +
            "                    }]\n" +
            "    }\n" +
            "}")
    List<ItemDocument> findAllByTypeAndBrand(String text, String brand, String type, Pageable pageable);

    @Query("{\n" +
            "\"bool\": { \n" +
            "      \"must\": [\n" +
            "{\"match\": {\n" +
            "      \"type\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"2\",\n" +
            "        \"boost\": \"1\"\n" +
            "      }\n" +
            "    }}  ],\n" +
            "      \n" +
            "      \"filter\":  [{\"match\":{\n" +
            "                    \"catalogueId\": \n" +
            "                        \"?1\"}\n" +
            "                    }]\n" +
            "    }\n" +
            "}")
    List<ItemDocument> find(String text, Long catalogueId, Pageable pageable);
    @Query("{\n" +
            "\"bool\": { \n" +
            "      \"must\": [\n" +
            "{\"match\": {\n" +
            "      \"type\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"fuzziness\": \"2\"\n" +
            "      }\n" +
            "    }}  ],\n" +
            "      \n" +
            "      \"filter\":  [{\"match\":{\n" +
            "                    \"type\": \n" +
            "                        \"?2\"}\n" +
            "                    },{\"match\":{\n" +
            "                    \"catalogueId\": \n" +
            "                        \"?1\"}\n" +
            "                    }]\n" +
            "    }\n" +
            "}")
    List<ItemDocument> find(String text, Long catalogueId, String type, Pageable pageable);
    @Query("{\"term\": {\n" +
            "      \"item_id\":  \"?0\"\n" +
            "    }}")
    List<ItemDocument> findByItemId(String itemId, PageRequest of);

    @Query("{\"regexp\": {\n" +
            "      \"name\": \"?0\" }}")
    List<ItemDocument> findAllByName(String name, Pageable pageable);
}