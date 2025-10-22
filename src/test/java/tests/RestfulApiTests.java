package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestfulApiTests {

    private static String baseUrl = "https://api.restful-api.dev/objects";
    private static String createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = baseUrl;
    }

    // 1 - GET all
    @Test
    @Order(1)
    public void testGetAllObjects() {
        given()
                .when().get()
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("size()", greaterThan(0));
    }

    // 2 - POST create object
    @Test
    @Order(2)
    public void testCreateObject() {
        String jsonBody = """
            {
              "name": "Notebook Teste QA",
              "data": {
                "year": 2025,
                "price": 999.99,
                "CPU model": "Intel Core i7",
                "Hard disk size": "1 TB"
              }
            }
            """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post()
                .then().statusCode(200)
                .and().extract().response();

        createdId = response.jsonPath().getString("id");
        assertNotNull(createdId, "ID do objeto criado não deve ser nulo");
    }

    // 3 - GET created object
    @Test
    @Order(3)
    public void testGetCreatedObject() {
        given()
                .when().get("/" + createdId)
                .then().statusCode(200)
                .and().body("id", equalTo(createdId))
                .and().body("name", equalTo("Notebook Teste QA"));
    }

    // 4 - PUT update
    @Test
    @Order(4)
    public void testUpdateObject() {
        String updateJson = """
            {
              "name": "Notebook Atualizado",
              "data": {
                "year": 2026,
                "price": 1099.90
              }
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updateJson)
                .when().put("/" + createdId)
                .then().statusCode(200)
                .and().body("name", equalTo("Notebook Atualizado"));
    }

    // 5 - PATCH partial update
    @Test
    @Order(5)
    public void testPartialUpdateObject() {
        String patchJson = """
            {
              "name": "Notebook Patched"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(patchJson)
                .when().patch("/" + createdId)
                .then().statusCode(200)
                .and().body("name", equalTo("Notebook Patched"));
    }

    // 6 - GET invalid ID
    @Test
    @Order(6)
    public void testGetInvalidObject() {
        given()
                .when().get("/99999999")
                .then().statusCode(anyOf(is(404), is(400)));
    }

    // 7 - Ensure created object appears in list
    @Test
    @Order(7)
    public void testListContainsCreatedId() {
        given()
                .whe
