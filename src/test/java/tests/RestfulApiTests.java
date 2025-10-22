package test.java.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestfulApiTests {


    // Base URL apontando para o domínio informado por você
    private static String baseUrl = "https://restful-api.dev";
    private static String createdId;


    @Test
    @Order(1)
    public void testGetAllObjects() {
        RestAssured.given()
                .when().get(baseUrl + "/objects")
                .then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }


    @Test
    @Order(2)
    public void testCreateObject() {
        String body = """
        {
        "name": "Test Object",
        "data": {
        "year": 2025,
        "price": 199.99,
        "CPU model": "Intel Core i9",
        "Hard disk size": "1 TB"
        }
        }
        """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(baseUrl + "/objects");


        response.then().statusCode(200)
                .body("name", equalTo("Test Object"));


        createdId = response.jsonPath().getString("id");
        Assertions.assertNotNull(createdId);
    }
}