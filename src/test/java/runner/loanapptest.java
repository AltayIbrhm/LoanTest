package runner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.ConfigurationReader;
import test_util.loanAppValidation_util;

import java.io.File;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
public class loanapptest extends loanAppValidation_util {


    @DisplayName("POST declined /Verify 200")
    @Test
    public  void loanapptestdeclined(){

        String api_key =  ConfigurationReader.getProperty("api_key");
        String targeturl= ConfigurationReader.getProperty("target_url");


        Map<String ,Object>filluploan
                = loanAppValidation_util.fillinguptheloan();


        given()
                .header("x-api-key", api_key)
                .contentType(ContentType.JSON)
                .body(filluploan)
                .when()
                .post(targeturl )
                .then()
                .log().all()
                .statusCode(200)
                .body("request.socialSecurityNumber",is(filluploan.get("socialSecurityNumber")))
                .body("request.leadOfferId",is(filluploan.get("leadOfferId")))
                .body("request.email",is(filluploan.get("email")))
                .body("request.stateCode",is(filluploan.get("stateCode")))
                .body("request.requestedLoanAmount",is(filluploan.get("requestedLoanAmount")))
                .body("request.grossMonthlyIncome",is(filluploan.get("grossMonthlyIncome")))
                .body("accepted",is(false))
                .body("status",is(ConfigurationReader.getProperty("declined")))
        //we can also assert the rest of the body.if we needed.

        ;


    }

    @DisplayName("POST accepted /Verify 200")
    @Test
    public  void loanapptestaccepted(){

        String api_key =  ConfigurationReader.getProperty("api_key");
        String targeturl= ConfigurationReader.getProperty("target_url");

        File file = new File("src/test/java/test_util/info.js");


        given()
                .header("x-api-key", api_key)
                .contentType(ContentType.JSON)
                .body(file)
                .when()
                .post(targeturl )
                .then()
                .log().all()
                .statusCode(200)
                .body("accepted",is(true))
                .body("status",is(ConfigurationReader.getProperty("accepted")))
    //we can also assert the rest of the body.if we needed.
        ;
    }


    @DisplayName("POST  /Verify 400")
    @Test
    public  void loanapptest_missinfo(){

        String api_key =  ConfigurationReader.getProperty("api_key");
        String targeturl= ConfigurationReader.getProperty("target_url");

        File file = new File("src/test/java/test_util/info.js");


        given()
                .header("x-api-key", api_key)
                .contentType(ContentType.JSON)
              .body(file+"}")
                    //added extra } to get the 400
                .when()
                .post(targeturl )
                .then()
                .log().all()
                .statusCode(400)


        ;
    }
    @DisplayName("POST  /Verify 403")
    @Test
    public  void loanapptestverfiy_auth(){

        String api_key =  ConfigurationReader.getProperty("api_key_wrong");
        String targeturl= ConfigurationReader.getProperty("target_url");


        Map<String ,Object>filluploan
                = loanAppValidation_util.missinfo();


        given()
                .header("x-api-key", api_key)
                .contentType(ContentType.JSON)
                .body(filluploan)
                .when()
                .post(targeturl )
                .then()
                .log().all()
                .statusCode(403)


        ;
    }

    @DisplayName("POST  /Verify 404")
    @Test
    public  void loanapptest(){

        String api_key =  ConfigurationReader.getProperty("api_key_wrong");
        String targeturl= ConfigurationReader.getProperty("target_url");


        Map<String ,Object>filluploan
                = loanAppValidation_util.missinfo();


        given()
                .header("x-api-key", api_key)
                .contentType(ContentType.JSON)
                .body(filluploan)
                .when()
                .get(targeturl )
                //used the get instead of post for to verify 404
                .then()
                .log().all()
                .statusCode(404)

        ;
    }
}
