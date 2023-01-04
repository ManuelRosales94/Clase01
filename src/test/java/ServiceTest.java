import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ServiceTest {

    @Test
    public void createUser(){
        RestAssured.given()
                .log().all()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"leader\"\n" +
                                "}")
                        .post("https://reqres.in/api/users")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body("name",equalTo("morpheus"))
                        .body("id", notNullValue());

    }

    @Test
    public void listaUser(){
        RestAssured.given()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page",equalTo(2));

        }

    @Test
    public void singleUser(){
        String response = RestAssured.given()
                .log().all()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all().toString();
        System.out.println(response);
    }

    @Test
    public void userNotfound(){
        String response = RestAssured.given()
                .log().all()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all().toString();
        System.out.println(response);
    }
    @Test
    public void listResource(){
        RestAssured.given()
                .log().all()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all().body("data[0].id",equalTo(1));
    }


    @Test
    public void updateUser(){
        String response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"manuel\",\n" +
                        "    \"job\": \"QA automation\"\n" +
                        "}")
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all().toString();
        System.out.println(response);

    }
    @Test
    public void registerSuccessful() {
         RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .post("https://reqres.in/api/register")
                .then()
                .log().all().statusCode(200)
                 .body("token",notNullValue())
                 .body("id",equalTo(4));
    }
    @Test
    public void loginSuccessful(){
        String response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all().toString();
        System.out.println(response);
    }

    @Test
    public void delayedResponse() {
        String response = RestAssured.given()
                .log().all()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all().toString();
        System.out.println(response);
    }



}
