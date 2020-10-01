import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ApiTest {
    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    void getTest() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .log()
                .body()
                .extract()
                .response();

    }
}
