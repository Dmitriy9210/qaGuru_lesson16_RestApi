import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static helpers.WorkWithFiles.readStringFromFile;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    void getTestUser() {
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

    @Test
    void postTestFailledRegistration() {
        String data = readStringFromFile("src/test/resources/register_unsuccessful.json");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("register")
                .then()
                .log().body().statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void postTestSuccessRegistration() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .when()
                .post("register")
                .then()
                .log().body().statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    void putTestSuccessUpdate() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"lalal@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .when()
                .put("/users/2")
                .then()
                .log().body().statusCode(200)
                .body("email", is(hasToString("lalal@reqres.in")));
    }

    @Test
    void deleteTestSuccessUpdate() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/users/3")
                .then()
                .log().body().statusCode(204);
    }
}
