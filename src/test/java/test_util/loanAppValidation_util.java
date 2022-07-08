package test_util;


import  static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class loanAppValidation_util {



    @AfterAll
    public  static  void cleanup(){
        reset();
        DB_Utility.destroy();
    }

    public  static Map<String,Object>fillinguptheloan(){

        Map<String ,Object>bodyMap = new LinkedHashMap<>();

        bodyMap.put("socialSecurityNumber","867530900");
        bodyMap.put("leadOfferId","kgj25sdd2");
        bodyMap.put("email","test@example.com");
        bodyMap.put("stateCode","IL");
        bodyMap.put("requestedLoanAmount","4000");
        bodyMap.put("grossMonthlyIncome","2800");

        return bodyMap;
    }

    public  static Map<String,Object>missinfo(){

        Map<String ,Object>bodyMap = new LinkedHashMap<>();

      bodyMap.put("socialSecurityNumber",null);

        return bodyMap;
    }

}
