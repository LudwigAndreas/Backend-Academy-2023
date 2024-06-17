package ru.shop.backend.search.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import ru.shop.backend.search.core.item_search.ItemSearchCoreService;
import ru.shop.backend.search.core.item_search_criteria.ItemSearchCriteriaDirector;
import ru.shop.backend.search.core.item_search_criteria.ItemSearchCriteriaUrlBuilder;
import ru.shop.backend.search.dto.Category;
import ru.shop.backend.search.dto.SearchResult;
import ru.shop.backend.search.dto.TypeHelpText;
import ru.shop.backend.search.dto.TypeOfQuery;
import ru.shop.backend.search.dto.request.SearchRequestModel;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;
import ru.shop.backend.search.model.db.Item;
import ru.shop.backend.search.model.elastic.CatalogueElasticDto;
import ru.shop.backend.search.model.elastic.ItemDocument;
import ru.shop.backend.search.repository.ItemDbRepository;
import ru.shop.backend.search.repository.ItemElasticsearchRepository;
import ru.shop.backend.search.service.ItemSearchService;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
//
//    private final ItemElasticsearchRepository repo;
//    private final ItemDbRepository repoDb;
//
//    private final Pageable PAGE_OF_150 = PageRequest.of(0, 150);
//
//    private final Pageable PAGE_OF_10 = PageRequest.of(0, 10);
//
//    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");
//
//    public static boolean isNumeric(String strNum) {
//        if (strNum == null) {
//            return false;
//        }
//        return DIGIT_PATTERN.matcher(strNum).matches();
//    }
//
//    public synchronized SearchResult getSearchResult(Integer regionId, String text) {
//        List<CatalogueElasticDto> result;
//
////        пытаемся найти по числу
//        if (isNumeric(text)) {
//            result = handleNumericText(text);
//        } else {
////            пытаемся найти по тексту
//            result = getAll(text);
//        }
//
////        фильтруем по региону
//        List<Item> items = retrieveItemsForRegionAndResult(regionId, result);
//
////        получаем первый бренд
//        String brand = extractBrand(result);
//
////        генерируем категории
//        List<Category> categories = generateCategories(items, brand);
//
////        возвращаем результат
//        return createSearchResult(result, items, categories);
//    }
//
//    /* Если текст поиска - число
//     * 1. Пытаемся найти его по sku из базы
//     * 2. Если не нашли, то пытаемся найти по имени из Elasticsearch
//     * 3. Если не нашли, пытаемся найти по id из Elasticsearch
//     * */
//    private List<CatalogueElasticDto> handleNumericText(String text) {
//        Optional<Integer> itemId = repoDb.findAllItemsBySku(text).stream().findFirst();
//        if (itemId.isEmpty()) {
//            List<CatalogueElasticDto> catalogue = getByName(text);
//            if (!catalogue.isEmpty()) {
//                return catalogue;
//            }
//        } else {
//            return getByItemId(itemId.toString());
//        }
//        return Collections.emptyList();
//    }
//
//
//    /*
//    * Получаем список товаров по региону и результату поиска
//    *
//    * Все найденные результаты фильтруем по id региона в базе
//    * */
//    private List<Item> retrieveItemsForRegionAndResult(Integer regionId, List<CatalogueElasticDto> result) {
//        return repoDb.findAllItemsByRegionIdAndIdIn(
//                        regionId,
//                        result.stream()
//                                .flatMap(category -> category.getItems().stream())
//                                .map(ItemDocument::getItemId)
//                                .collect(Collectors.toList())
//                ).stream()
//                .map(arr -> new Item(
//                        ((BigInteger) arr[2]).intValue(),
//                        arr[1].toString(),
//                        arr[3].toString(),
//                        arr[4].toString(),
//                        ((BigInteger) arr[0]).intValue(),
//                        arr[5].toString())
//                )
//                .collect(Collectors.toList());
//    }
//
//    /*
//    * Получаем бренд из первого результата поиска
//    *
//    * Если результатов нет, то возвращаем пустую строку
//    * */
//    private String extractBrand(List<CatalogueElasticDto> result) {
//        if (!result.isEmpty()) {
//            String brand = result.get(0).getBrand();
//            return (brand != null) ? brand.toLowerCase(Locale.ROOT) : "";
//        }
//        return "";
//    }
//
//    /*
//    * Генерируем категории по результату поиска
//    *
//    * Если в результате поиска есть одинаковые категории, то оставляем только одну
//    * Если бренд пустой, то не добавляем его в ссылку
//    * Если в результате поиска нет категорий, то возвращаем пустой список
//    * */
//    private List<Category> generateCategories(List<Item> items, String brand) {
//        Set<String> catUrls = new HashSet<>();
//        String finalBrand = brand.toLowerCase(Locale.ROOT);
//
//        return repoDb.findAllCategoriesByIdIn(
//                        items.stream().map(Item::getItemId).collect(Collectors.toList())
//                ).stream()
//                .map(arr -> {
//                    if (catUrls.contains(arr[2].toString())) {
//                        return null;
//                    }
//                    catUrls.add(arr[2].toString());
//                    return new Category(
//                            arr[0].toString(),
//                            arr[1].toString(),
//                            "/cat/" + arr[2].toString() + (finalBrand.isEmpty() ? "" : "/brands/" + finalBrand),
//                            "/cat/" + arr[3].toString(),
//                            arr[4] == null ? null : arr[4].toString()
//                    );
//                })
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//
//    /*
//    * Создаем результат поиска
//    *
//    * 1. Если результат пустой, то возвращаем пустой результат
//    * 2. Если результат не пустой, то возвращаем результат поиска и SEE_ALSO тип первого товара, если он есть
//    *
//    * */
//    private SearchResult createSearchResult(List<CatalogueElasticDto> result, List<Item> items, List<Category> categories) {
//        List<TypeHelpText> typeHelpTexts = !result.isEmpty() ?
//                List.of(new TypeHelpText(TypeOfQuery.SEE_ALSO,
//                        ((result.get(0).getItems().get(0).getType() != null ? result.get(0).getItems().get(0).getType() : "") +
//                                " " + (result.get(0).getBrand() != null ? result.get(0).getBrand() : "")).trim())) :
//                new ArrayList<>();
//
//        return new SearchResult(items, categories, typeHelpTexts);
//    }
//
//    /*
//    * Если текст поиска - не число
//    *
//    * */
//    public synchronized List<CatalogueElasticDto> getAll(String text) {
//        return getAll(text, PAGE_OF_10);
//    }
//
//
//    /*
//    *
//    *
//    * */
//    public List<CatalogueElasticDto> getAll(String text, Pageable pageable) {
//        String type = "";
//        List<ItemDocument> list = new ArrayList<>();
//        String brand = "";
//        String text2 = text;
//        Long catalogueId = null;
//        boolean needConvert = true;
//
////        если в тексте есть недопустимые символы, то пробуем их конвертировать
//        if (isContainErrorChar(text)) {
//            text = convert(text);
//            needConvert = false;
//        }
////        если в тексте есть недопустимые символы, которые не удалось конвертировать, то не конвертируем
//        if (needConvert && isContainErrorChar(convert(text))) {
//            needConvert = false;
//        }
//
////        если в тексте есть пробелы, то пробуем найти по бренду
//        if (text.contains(" ")) {
//            for (String queryWord : text.split("\\s")) {
//                list = repo.findAllByBrand(queryWord, pageable);
//                if (list.isEmpty() && needConvert) {
//                    list = repo.findAllByBrand(convert(text), pageable);
//                }
////                если нашли, то удаляем из текста
//                if (!list.isEmpty()) {
//                    text = text.replace(queryWord, "").trim().replace("  ", " ");
//                    brand = list.get(0).getBrand();
//                    break;
//                }
//
//            }
////          пробуем найти по типу
//            list = repo.findAllByType(text, pageable);
//        }
////        если не нашли, то пробуем найти по типу, но конвертируем текст
//        if (list.isEmpty() && needConvert) {
//            list = repo.findAllByType(convert(text), pageable);
//        }
////        если нашли, то указываем тип минимальной длины
//        if (!list.isEmpty()) {
//            type = (list.stream().map(ItemDocument::getType).min(Comparator.comparingInt(String::length)).get());
//        } else {
////            если не нашли, то пробуем найти по типу
//            for (String queryWord : text.split("\\s")) {
//                list = repo.findAllByType(queryWord, pageable);
////                если не нашли, то пробуем найти по типу и бренду, но конвертируем текст
//                if (list.isEmpty() && needConvert) {
//                    list = repo.findAllByType(convert(text), pageable);
//                }
////                если нашли, то указываем тип минимальной длины и удаляем из текста
//                if (!list.isEmpty()) {
//                    text = text.replace(queryWord, "");
//                    type = (list.stream().map(ItemDocument::getType).min(Comparator.comparingInt(String::length)).get());
//                }
//            }
//        }
////        если не нашли бренд, то пробуем найти по каталогу
//        if (brand.isEmpty()) {
//            list = repo.findByCatalogue(text, pageable);
////            если не нашли, то пробуем найти по каталогу, но конвертируем текст
//            if (list.isEmpty() && needConvert) {
//                list = repo.findByCatalogue(convert(text), pageable);
//            }
////            если нашли, то указываем каталог
//            if (!list.isEmpty()) {
//                catalogueId = list.get(0).getCatalogueId();
//            }
//        }
//        text = text.trim();
////        если текст пустой и нашли бренд, то возвращаем каталог с брендом
//        if (text.isEmpty() && !brand.isEmpty())
//            return Collections.singletonList(new CatalogueElasticDto(list.get(0).getCatalogue(), list.get(0).getCatalogueId(), null, brand));
//
////        добавляем знаки вопроса в конец текста
//        text += "?";
////        если бренд пустой
//        if (brand.isEmpty()) {
//            type += "?";
////            если каталог пустой, то пробуем найти по типу
//            if (catalogueId == null) {
//                list = repo.findAllByType(text, type, pageable);
//                if (list.isEmpty()) {
////                    если не нашли, то пробуем найти по типу, но конвертируем текст
//                    list = repo.findAllByType(convert(text), type, pageable);
//                }
//            } else {
////                если каталог не пустой, то пробуем найти по типу и каталогу
//                list = repo.find(text, catalogueId, pageable);
//                if (list.isEmpty()) {
////                    если не нашли, то пробуем найти по типу и каталогу, но конвертируем текст
//                    list = repo.find(convert(text), catalogueId, pageable);
//                }
//            }
////            если бренд не пустой
//        } else {
////            если тип пустой, то пробуем найти по бренду
//            if (type.isEmpty()) {
//                list = repo.findAllByBrand(text, brand, pageable);
//                if (list.isEmpty()) {
//                    list = repo.findAllByBrand(convert(text), brand, pageable);
//                }
////                если тип не пустой, то пробуем найти по типу и бренду
//            } else {
//                type += "?";
//                list = repo.findAllByTypeAndBrand(text, brand, type, pageable);
//                if (list.isEmpty()) {
//                    list = repo.findAllByTypeAndBrand(convert(text), brand, type, pageable);
//                }
//            }
//        }
////        если результат пустой
//        if (list.isEmpty()) {
////            если текст содержит пробелы, то пробуем найти по тексту без пробелов
//            if (text2.contains(" "))
//                text = String.join(" ", text.split("\\s"));
//            text2 += "?";
//            list = repo.findAllNotStrong(text2, pageable);
////            если не нашли, то пробуем найти по тексту без пробелов, но конвертируем текст
//            if (list.isEmpty() && needConvert) {
//                list = repo.findAllByTypeAndBrand(convert(text2), brand, type, pageable);
//            }
//        }
//
//        return get(list, text, brand);
//    }
//
//    /*
//    *
//    * */
//    private List<CatalogueElasticDto> get(List<ItemDocument> list, String name, String brand) {
////        для всего результата, текста и бренда
//        Map<String, List<ItemDocument>> map = new HashMap<>();
//        AtomicReference<ItemDocument> searchedItem = new AtomicReference<>();
////        для каждого элемента
//        list.forEach(
//                i ->
//                {
////                    если имя элемента совпадает с текстом, то сохраняем элемент
//                    if (name.replace("?", "").equals(i.getName())) {
//                        searchedItem.set(i);
//                    }
////                    если имя элемента заканчивается на текст и начинается с типа, то сохраняем элемент
//                    if (name.replace("?", "").endsWith(i.getName()) && name.replace("?", "").startsWith(i.getType())) {
//                        searchedItem.set(i);
//                    }
////                    если мапа не содержит каталог, то добавляем каталог
//                    if (!map.containsKey(i.getCatalogue())) {
//                        map.put(i.getCatalogue(), new ArrayList<>());
//                    }
//                    map.get(i.getCatalogue()).add(i);
//                }
//        );
//        if (brand.isEmpty())
//            brand = null;
////        если нашли элемент, то возвращаем каталог с элементом
//        if (searchedItem.get() != null) {
//            ItemDocument i = searchedItem.get();
//            return Collections.singletonList(new CatalogueElasticDto(i.getCatalogue(), i.getCatalogueId(), Collections.singletonList(i), brand));
//        }
////        иначе возвращаем каталог с элементами по бренду
//        String finalBrand = brand;
//        return map.keySet().stream().map(c ->
//                new CatalogueElasticDto(c, map.get(c).get(0).getCatalogueId(), map.get(c), finalBrand)).collect(Collectors.toList());
//    }
//
//    /*
//    *
//    * */
//    public List<CatalogueElasticDto> getByName(String num) {
//        List<ItemDocument> list = repo.findAllByName(".*" + num + ".*", PAGE_OF_150);
//        return get(list, num, "");
//    }
//
//    /*
//    *
//    * */
//    public List<CatalogueElasticDto> getByItemId(String itemId) {
//        var list = repo.findByItemId(itemId, PageRequest.of(0, 1));
//        return Collections.singletonList(new CatalogueElasticDto(list.get(0).getCatalogue(), list.get(0).getCatalogueId(), list, list.get(0).getBrand()));
//    }
//
////    TODO: не поддерживает заглавные буквы
//    public static String convert(String message) {
//        boolean result = message.matches(".*\\p{InCyrillic}.*");
//        char[] ru = {'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ', 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э', 'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю', '.',
//                ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};
//        char[] en = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '"', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/',
//                ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};
//        StringBuilder builder = new StringBuilder();
//
//        if (result) {
//            for (int i = 0; i < message.length(); i++) {
//                for (int j = 0; j < ru.length; j++) {
//                    if (message.charAt(i) == ru[j]) {
//                        builder.append(en[j]);
//                    }
//                }
//            }
//        } else {
//            for (int i = 0; i < message.length(); i++) {
//                for (int j = 0; j < en.length; j++) {
//                    if (message.charAt(i) == en[j]) {
//                        builder.append(ru[j]);
//                    }
//                }
//            }
//        }
//        return builder.toString();
//    }
//
//    private Boolean isContainErrorChar(String text) {
//        return text.contains("[") || text.contains("]") || text.contains("\"") || text.contains("/") || text.contains(";");
//    }
//
//    public List<CatalogueElasticDto> getAllFull(String text) {
//        return getAll(text, PAGE_OF_150);
//    }


    private final ItemSearchCoreService elasticsearchService;

    private final ItemDbRepository itemDbRepository;

    @Override
    public SearchResult search(SearchRequestModel searchRequestModel) {
        try {
            ItemSearchCriteriaUrlBuilder builder = new ItemSearchCriteriaUrlBuilder(searchRequestModel);
            ItemSearchCriteriaDirector director = new ItemSearchCriteriaDirector(builder);
            director.buildCriteria();

            ItemSearchCriteria criteria = director.getCriteria();
            SearchPage<ItemDocument> searchPage = elasticsearchService.search(criteria);
            List<Item> filtredItems = filterByRegionId(searchPage, criteria);

//            TODO: inject mapping logic
            SearchResult searchResult = new SearchResult();
            searchResult.setCategories(Collections.emptyList());
            searchResult.setTypeQueries(Collections.emptyList());
            searchResult.setItems(filtredItems);

            return searchResult;
        } catch (Throwable e) {
//            TODO: add exception handling
//            log.error("Error while searching", e);
//            return new SearchResult();
            e.printStackTrace();
            return null;
        }
    }

    private List<Item> filterByRegionId(SearchPage<ItemDocument> searchPage, ItemSearchCriteria criteria) {
        if (criteria.getRegionId() != null) {
            List<Long> itemIds = searchPage.stream()
                    .map(SearchHit::getContent)
                    .map(ItemDocument::getItemId)
                    .collect(Collectors.toList());
            List<Item> items = itemDbRepository.findAllItemsByRegionIdAndIdIn(criteria.getRegionId(), itemIds)
                    .stream()
                    .map(
                            arr -> new Item(
                                    ((BigInteger) arr[0]).longValue(),
                                    arr[1].toString(),
                                    arr[3].toString(),
                                    ((BigInteger) arr[2]).longValue(),
                                    arr[4].toString(),
                                    arr[5].toString()
                            )
                    ).collect(Collectors.toList());
            return items;
        }
        return searchPage.stream()
                .map(SearchHit::getContent)
                .map(
                        itemDocument -> new Item(
                                itemDocument.getItemId(),
                                itemDocument.getName(),
                                itemDocument.getBrand(),
                                itemDocument.getCatalogueId(),
                                itemDocument.getCatalogue(),
                                itemDocument.getType()
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public String getRawJsonQuery(SearchRequestModel searchRequestModel) {
        ItemSearchCriteriaUrlBuilder builder = new ItemSearchCriteriaUrlBuilder(searchRequestModel);
        ItemSearchCriteriaDirector director = new ItemSearchCriteriaDirector(builder);
        director.buildCriteria();

        ItemSearchCriteria criteria = director.getCriteria();
        return elasticsearchService.getRawJsonQuery(criteria);
    }
}
