package ru.anosvqa.vae.rest_api_restassured_tests;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredTests {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.port = 443;
    }

    @Test
    public void whenMeasureResponseTime(){
        Response response = RestAssured.get("/users/crazair");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);

        assertEquals(timeInS, timeInMS/1000);
    }

    @Test
    public void whenValidateResponseTime(){
        when().get("/users/crazair").then()
                .time(lessThan(5000L));
    }

    @Test
    public void whenValidateResponseTimeInSeconds(){
        when().get("/users/crazair").then()
                .time(lessThan(5L), TimeUnit.SECONDS);
    }

    @Test
    public void whenUseQueryParam(){
        given().queryParam("q", "john")
                .when().get("/search/users").then().statusCode(200);
        given().param("q", "john")
                .when().get("/search/users").then().statusCode(200);
    }

    @Test
    public void whenUseMultipleQueryParam(){
        int perPage = 20;

        given().queryParam("q", "john")
                .queryParam("per_page",perPage).when().get("/search/users")
                .then().body("items.size()", is(perPage));

        given().queryParams("q", "john","per_page",perPage)
                .when().get("/search/users")
                .then().body("items.size()", is(perPage));
    }

    @Test
    public void whenUseFormParam(){
        given().log().all().formParams("username", "john","password","1234").post("/");
        given().log().all().params("username", "john","password","1234").post("/");
    }

    @Test
    public void whenUsePathParam(){
        given().pathParam("user", "crazair")
                .when().get("/users/{user}/repos").then().log().all().statusCode(200);
    }

    @Test
    public void whenUseMultiplePathParamK(){
        given().log().all().pathParams("owner", "crazair","repo", "various_autotests_exx")
                .when().get("/repos/{owner}/{repo}")
                .then().statusCode(200);
        given().log().all().pathParams("owner", "crazair")
                .when().get("/repos/{owner}/{repo}","various_autotests_exx")
                .then().statusCode(200);
    }

    @Test
    public void whenUseCustomHeader(){
        given().header("User-Agent", "MyAppName")
                .when().get("/users/crazair").then().statusCode(200);
    }

    @Test
    public void whenUseMultipleHeaders(){
        given().header("User-Agent", "MyAppName","Accept-Charset","utf-8")
                .when().get("/users/crazair").then().statusCode(200);
    }

    @Test
    public void whenUseCookie(){
        given().cookie("session_id", "1234")
                .when().get("/users/crazair")
                .then().statusCode(200);
    }

    @Test
    public void whenUseCookieBuilder(){
        Cookie myCookie = new Cookie.Builder("session_id", "1234").setSecured(true).setComment("session id cookie").build();
        given().cookie(myCookie).when().get("/users/crazair").then().statusCode(200);
    }

    @Test
    public void whenRequestGet(){
        when().request("GET", "/users/crazair")
                .then().statusCode(200);
    }

    @Test
    public void whenRequestHead(){
        when().request("HEAD", "/users/crazair")
                .then().statusCode(200);
    }

    @Test
    public void whenLogRequest(){
        given().log().all().when().get("/users/crazair")
                .then().statusCode(200);
    }

    @Test
    public void whenLogResponse(){
        when().get("/repos/crazair/various_autotests_exx").then().log().body().statusCode(200);
    }

    @Test
    public void whenLogResponseIfErrorOccurred(){
        when().get("/users/crazair").then().log().ifError();
        when().get("/users/crazair").then().log().ifStatusCodeIsEqualTo(500);
        when().get("/users/crazair").then().log().ifStatusCodeMatches(greaterThan(200));
    }

    @Test
    public void whenLogOnlyIfValidationFailed(){
        when().get("/users/crazair").then().log().ifValidationFails().statusCode(200);
        given().log().ifValidationFails().when().get("/users/crazair").then().statusCode(200);
    }
}
