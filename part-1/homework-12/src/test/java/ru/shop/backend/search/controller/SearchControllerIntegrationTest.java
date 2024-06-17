package ru.shop.backend.search.controller;

import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.persistence.EntityManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SearchControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    @Getter
    private int port;
    private final EntityManager entityManager;


    @BeforeAll
    static void setUp() {
    }

    @Autowired
    public SearchControllerIntegrationTest(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    @Order(1)
    void test_container_env() {
        List<?> items = entityManager.createNativeQuery("select * from item").getResultList();
        assertThat(items.size(), greaterThan(0));
    }

    @Test
    void find_EmptyList_IfTextIsBlank() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=")
        .then()
                .statusCode(200)
                .body("items.size()", is(0),
                        "categories.size()", is(0),
                        "typeQueries.size()", is(0));
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsSkuNumeric() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=100001")
        .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics")
                );
    }

//    @Test
//    void find_ItemAndTypeQueries_IfTextIsSkuAlphanumeric() {
//        given()
//                .cookie("regionId", "1")
//        .when()
//                .get("http://localhost:" + port + "/api/search?text=SKU006")
//        .then()
//                .statusCode(200)
//                .body("items.size()", is(1),
//                        "items[0].name", is("Formal Shirt"),
//                        "items[0].url", is("formalshirt.com"),
//                        "items[0].itemId", is(106),
//                        "items[0].cat", is("Clothing")
//                );
//    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsName() {
//        TODO: check
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Laptop Model X")
        .then()
                .statusCode(200)
                .body("items.size()", is(2),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics"),
                        "items[1].name", is("Smartwatch Series Z"),
                        "items[1].url", is("smartwatchz.com"),
                        "items[1].itemId", is(103),
                        "items[1].cat", is("Electronics")
                );
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsPartOfName() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Wireless")
        .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Wireless Earbuds"),
                        "items[0].url", is("wirelessearbuds.com"),
                        "items[0].itemId", is(110),
                        "items[0].cat", is("Accessories")
                );
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsCategory() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Laptops")
        .then()
                .statusCode(200)
                .body("items.size()", is(2),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics"),
                        "items[1].name", is("Smartwatch Series Z"),
                        "items[1].url", is("smartwatchz.com"),
                        "items[1].itemId", is(103),
                        "items[1].cat", is("Electronics")
                );
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsPartOfCategory() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=lptp")
        .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics")
                );
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsType() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Electronics")
        .then()
                .statusCode(200)
                .body("items.size()", is(2),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics"),
                        "items[1].name", is("Smartwatch Series Z"),
                        "items[1].url", is("smartwatchz.com"),
                        "items[1].itemId", is(103),
                        "items[1].cat", is("Electronics")
                );
    }


    @Test
    void find_ItemAndTypeQueries_IfTextIsTypeAndRegionIdIsNotDefault() {
        given()
                .cookie("regionId", "2")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Electronics")
        .then()
                .statusCode(200)
                .body("items.size()", is(2),
                        "items[0].name", is("Tablet Pro"),
                        "items[0].url", is("tabletpro.com"),
                        "items[0].itemId", is(102),
                        "items[0].cat", is("Electronics"),
                        "items[1].name", is("Smartphone Y"),
                        "items[1].url", is("smartphoney.com"),
                        "items[1].itemId", is(104),
                        "items[1].cat", is("Electronics")
                );
    }

    @Test
    void find_EmptyItemsList_IfRegionIdIsNotExists() {
        given()
                .cookie("regionId", "0")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Electronics")
        .then()
                .statusCode(200)
                .body("items.size()", is(0));
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsTypo() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Laptoo")
        .then()
                .statusCode(200)
                .body("items.size()", is(2),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics"),
                        "items[1].name", is("Smartwatch Series Z"),
                        "items[1].url", is("smartwatchz.com"),
                        "items[1].itemId", is(103),
                        "items[1].cat", is("Electronics")
                );

        given()
                .cookie("regionId", "1")
                .when()
                .get("http://localhost:" + port + "/api/search?text=Lptp")
                .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Laptop Model X"),
                        "items[0].url", is("laptopmodelx.com"),
                        "items[0].itemId", is(101),
                        "items[0].cat", is("Electronics")
                );
    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsTypoAndRegionIdIsNotDefault() {
        given()
                .cookie("regionId", "2")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Laaptop")
        .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Tablet Pro"),
                        "items[0].url", is("tabletpro.com"),
                        "items[0].itemId", is(102),
                        "items[0].cat", is("Electronics")
                );
    }

// TODO: check
//    @Test
//    void find_ItemAndTypeQueries_IfTextIsCyrrilic() {
//        given()
//                .cookie("regionId", "1")
//        .when()
//                .get("http://localhost:" + port + "/api/search?text=Рюкзак%20кролик")
//        .then()
//                .statusCode(200)
//                .body("items.size()", is(1),
//                        "items[0].name", is("Рюкзак кролик"),
//                        "items[0].url", is("backpack-rabbit.com"),
//                        "items[0].itemId", is(117),
//                        "items[0].cat", is("Backpacks")
//                );
//    }

    @Test
    void find_ItemAndTypeQueries_IfTextIsCyrrilicWithDash() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search?text=Рюкзак-сумка")
        .then()
                .statusCode(200)
                .body("items.size()", is(1),
                        "items[0].name", is("Рюкзак-сумка"),
                        "items[0].url", is("backpack-bag.com"),
                        "items[0].itemId", is(116),
                        "items[0].cat", is("Backpacks")
                );
    }

    @Test
    void findBy_ResultWithItem_IfTextIsSkuNumeric() {
        given()
                .cookie("regionId", "1")
        .when()
                .get("http://localhost:" + port + "/api/search/by?text=100001")
        .then()
                .statusCode(200)
                .body("result.size()", is(1),
                        "result[0].name", is("Laptops"),
                        "result[0].catalogueId", is(1),
                        "result[0].items.size()", is(1),
                        "result[0].items[0].name", is("Laptop Model X"),
                        "result[0].items[0].itemId", is(101),
                        "result[0].brand", is("BrandX")
                );
    }

//    @Test
//    void findBy_ResultWithItem_IfTextIsSkuAlphanumeric() {
//        given()
//                .cookie("regionId", "1")
//        .when()
//                .get("http://localhost:" + port + "/api/search/by?text=SKU006")
//        .then()
//                .statusCode(200)
//                .body("result", notNullValue(),
//                        "result.name", is("T-Shirts"),
//                        "result.catalogueId", is(2),
//                        "result.items.size()", is(1),
//                        "result.items[0].name", is("Formal Shirt"),
//                        "result.items[0].itemId", is(106),
//                        "result.brand", is("BrandI")
//                );
//    }

    @Test
    void findBy_ResultWithItem_IfTextIsSkuNumericAndRegionIdIsNotDefault() {
        given()
                .cookie("regionId", "2")
        .when()
                .get("http://localhost:" + port + "/api/search/by?text=100002")
        .then()
                .statusCode(200)
                .body("result.size()", is(1),
                        "result[0].name", is("Laptops"),
                        "result[0].catalogueId", is(1),
                        "result[0].items.size()", is(1),
                        "result[0].items[0].name", is("Tablet Pro"),
                        "result[0].items[0].itemId", is(102),
                        "result[0].brand", is("BrandG")
                );
    }

//    @Test
//    void findBy_ResultWithItem_IfTextIsSkuAlphanumericAndRegionIdIsNotDefault() {
//        given()
//                .cookie("regionId", "2")
//        .when()
//                .get("http://localhost:" + port + "/api/search/by?text=SKU006")
//        .then()
//                .statusCode(200)
//                .body("result.size()", is(1),
//                        "result[0].name", is("T-Shirts"),
//                        "result[0].catalogueId", is(2),
//                        "result[0].items.size()", is(1),
//                        "result[0].items[0].name", is("Formal Shirt"),
//                        "result[0].items[0].itemId", is(106),
//                        "result[0].brand", is("BrandI")
//                );
//    }










}
