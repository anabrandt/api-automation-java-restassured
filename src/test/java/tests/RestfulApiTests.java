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
                .when().get()
                .then().statusCode(200)
                .and().body("id", hasItem(createdId));
    }

    // 8 - PUT with invalid data
    @Test
    @Order(8)
    public void testUpdateWithInvalidData() {
        String invalidJson = """
            { "invalid": "data" }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when().put("/" + createdId)
                .then().statusCode(anyOf(is(400), is(500)));
    }

    // 9 - POST missing required name field
    @Test
    @Order(9)
    public void testCreateWithoutName() {
        String jsonBody = """
            { "data": { "price": 200.0 } }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post()
                .then().statusCode(anyOf(is(400), is(422), is(500)));
    }

    // 10 - Validate Content-Type header
    @Test
    @Order(10)
    public void testContentTypeHeader() {
        Response response = given()
                .when().get()
                .then().extract().response();

        assertEquals("application/json", response.getContentType());
    }

    // 11 - Performance test
    @Test
    @Order(11)
    public void testResponseTime() {
        given()
                .when().get()
                .then().time(lessThan(3000L));
    }

    // 12 - Create multiple objects
    @Test
    @Order(12)
    public void testCreateMultipleObjects() {
        for (int i = 0; i < 3; i++) {
            String jsonBody = String.format("""
                {
                  "name": "Device %d",
                  "data": { "year": 2025 }
                }
                """, i);

            given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when().post()
                    .then().statusCode(200);
        }
    }

    // 13 - GET all again
    @Test
    @Order(13)
    public void testGetAllObjectsAgain() {
        given()
                .when().get()
                .then().statusCode(200)
                .and().body("size()", greaterThanOrEqualTo(1));
    }

    // 14 - DELETE created object
    @Test
    @Order(14)
    public void testDeleteObject() {
        given()
                .when().delete("/" + createdId)
                .then().statusCode(anyOf(is(200), is(204)));
    }

    // 15 - GET deleted object should fail
    @Test
    @Order(15)
    public void testDeletedObjectNotFound() {
        given()
                .when().get("/" + createdId)
                .then().statusCode(anyOf(is(404), is(400)));
    }
}
