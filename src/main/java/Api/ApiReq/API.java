package Api.ApiReq;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class API {
    public static RequestSpecification apiSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://testslotegrator.com/api")
                .addFilter(new AllureRestAssured())
                .build();
    }
}
