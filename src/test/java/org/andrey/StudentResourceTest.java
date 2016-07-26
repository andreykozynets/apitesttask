package org.andrey;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ResponseBody;
import org.andrey.api.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0",
        "spring.datasource.url:jdbc:h2:mem:api;DB_CLOSE_ON_EXIT=FALSE"})

public class StudentResourceTest {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    public void testStudentCrud() throws Exception {

        //create
        ResponseBody body = given().contentType("application/json").
                body("{\"firstName\": \"Kevin\", \"lastName\": \"Created\"}").
                when().
                post("/student").getBody();

        String studentId = new String(body.asByteArray());

        //list and check the created item
        when().get("/student/list").then()
                .body(containsString("{\"id\":" + studentId + ",\"firstName\":\"Kevin\",\"lastName\":\"Created\"}"));

        //update
        given().contentType("application/json").
                body("{\"firstName\": \"Created\", \"lastName\": \"Kevin\"}").
                when().
                put("/student/" + studentId)
                .then().body(is("true"));

        //list and check the updated item
        when().get("/student/list").then()
                .body(containsString("{\"id\":" + studentId + ",\"firstName\":\"Created\",\"lastName\":\"Kevin\"}"));

        when().delete("/student/" + studentId).then().body(is("true"));

        //list and check the updated item is not there
        when().get("/student/list").then().body(not(containsString("{\"id\":" + studentId + ",\"firstName\":\"Created\",\"lastName\":\"Kevin\"}")));

    }

    @Test
    public void testListStudents() throws Exception {

        when().get("/student/list").then()
                .body(not(containsString("\"firstName\":\"Kevin\",\"lastName\":\"List\"}")),
                        not(containsString("\"firstName\":\"List\",\"lastName\":\"Kevin\"}")));

        //create 2 students
        given().contentType("application/json").
                body("{\"firstName\": \"Kevin\", \"lastName\": \"List\"}").
                when().
                post("/student");

        given().contentType("application/json").
                body("{\"firstName\": \"List\", \"lastName\": \"Kevin\"}").
                when().
                post("/student");

        when().get("/student/list").then()
                .body(containsString("\"firstName\":\"Kevin\",\"lastName\":\"List\"}"),
                        containsString("\"firstName\":\"List\",\"lastName\":\"Kevin\"}"));

    }

    @Test
    public void testErrorMessages() throws Exception {

        //create
        ResponseBody body = given().contentType("application/json").
                body("{\"firstName\": \"Kevin\", \"lastName\": \"Created\"}").
                when().
                post("/student").getBody();

        String studentId = new String(body.asByteArray());

        //list and check the created item
        when().get("/student/list").then()
                .body(containsString("{\"id\":" + studentId + ",\"firstName\":\"Kevin\",\"lastName\":\"Created\"}"));

        //corrupt studentId
        studentId = "12345678912345";

        //try to update with wrong id
        given().contentType("application/json").
                body("{\"firstName\": \"Created\", \"lastName\": \"Kevin\"}").
                when().
                put("/student/" + studentId)
                .then().body(is("{\"status\":404,\"errors\":[\"Student with id 12345678912345 does not exist\"]}"));

    }
}