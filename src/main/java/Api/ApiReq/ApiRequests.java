package Api.ApiReq;


import Api.ApiData.UserDataGetOne;
import Api.ApiData.UserDataReg;
import Api.ApiData.UserDataAuth;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiRequests extends API {


    @Step("Авторизация пользователя")
    public Response authRegisteredUser(UserDataAuth user) {
        return given()
                .spec(apiSpecification())
                .and()
                .body(user)
                .post("/tester/login");
    }

    @Step("Регистрация пользователя")
    public Response createUser(UserDataReg userDataReg, String token) {

        return given()
                .spec(apiSpecification())
                .and()
                .header("Authorization",
                        "Bearer " + token)
                .body(userDataReg)
                .when()
                .post("/automationTask/create");
    }


    @Step("Получение информации о пользователе")
    public Response GetUserInfo(UserDataGetOne user, String token) {

        return given()
                .spec(apiSpecification())
                .and()
                .header("Authorization",
                        "Bearer " + token)
                .body(user)
                .when()
                .post("/automationTask/getOne");
    }

    @Step("Получение всех пользователей")
    public Response getAllUsers(String token) {

        return given()
                .spec(apiSpecification())
                .and()
                .header("Authorization",
                        "Bearer " + token)
                .when()
                .get("/automationTask/getAll");
    }

    @Step("Получаем данные пользователя")
    public Response getOneUser(UserDataGetOne userDataGetOne, String token) {

        return given()
                .spec(apiSpecification())
                .and()
                .header("Authorization",
                        "Bearer " + token)
                .body(userDataGetOne)
                .when()
                .post("/automationTask/getOne");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String id, String token) {

        return given()
                .spec(apiSpecification())
                .and()
                .header("Authorization",
                        "Bearer " + token)
                .when()
                .delete("/automationTask/deleteOne/" + id);
    }


}