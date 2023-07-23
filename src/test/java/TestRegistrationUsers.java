import Api.ApiData.UserDataAuth;
import Api.ApiData.UserDataGetOne;
import Api.ApiData.UserDataReg;
import Api.ApiReq.ApiRequests;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class TestRegistrationUsers {

    public static ApiRequests apiRequests;
    public static UserDataAuth userDataAuth;
    public static UserDataGetOne userDataGetOne;
    public static String token;
    public UserDataReg userDataReg;
    public int statusCode;
    private static List<String> idNewUsers = new ArrayList<>();
    private static List<String> forSort = new ArrayList<>();

    public TestRegistrationUsers(UserDataReg userDataReg, int statusCode) {
        this.userDataReg = userDataReg;
        this.statusCode = statusCode;
    }

    private void verifyResponse(Response response, int statusCode) {
        response.then().statusCode(statusCode);
        //Там нейминг разный в API
        //assertNotNull(response.body().path("id"));
        assertNotNull(response.body().path("currency_code"));
        assertNotNull(response.body().path("email"));
        assertNotNull(response.body().path("surname"));
        assertNotNull(response.body().path("username"));
    }


    @BeforeClass
    public static void setUp() {
        apiRequests = new ApiRequests();
        //Нужно ввести данные для авторизации
        userDataAuth = new UserDataAuth("EMAIL", "PASSWORD");
        Response resp = apiRequests.authRegisteredUser(userDataAuth);
        resp.then().statusCode(SC_CREATED);
        token = resp.body().path("accessToken");

    }

    @Parameterized.Parameters(name = "Тестовые данные: {1}")
    public static Object[][] getDataForRegistration() {
        return new Object[][]{
                {new UserDataReg("EUR", "password", "password", "testUser1", "pTest", "pTest", "test1@slotegrator.com"), SC_CREATED},
                {new UserDataReg("USD", "password", "password", "testUser2", "lTest", "lTest", "test2@slotegrator.com"), SC_CREATED},
                {new UserDataReg("RUB", "password", "password", "testUser3", "zTest", "zTest", "test3@slotegrator.com"), SC_CREATED},
                {new UserDataReg("AZM", "password", "password", "testUser4", "dTest", "dTest", "test4@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BGL", "password", "password", "testUser5", "vTest", "vTest", "test5@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BHD", "password", "password", "testUser6", "fTest", "fTest", "test6@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BIF", "password", "password", "testUser7", "jTest", "jTest", "test7@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BMD", "password", "password", "testUser8", "hTest", "hTest", "test8@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BND", "password", "password", "testUser9", "sTest", "sTest", "test9@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BSD", "password", "password", "testUser10", "kTest", "kTest", "test10@slotegrator.com"), SC_CREATED},
                {new UserDataReg("BTN", "password", "password", "testUser11", "cTest", "cTest", "test11@slotegrator.com"), SC_CREATED},
                {new UserDataReg("AED", "password", "password", "testUser12", "aTest", "aTest", "test12@slotegrator.com"), SC_CREATED},

        };
    }

    @Test
    @DisplayName("Регистрация, получение нового пользователя и удаление пользователя | позитивный тест")
    public void registrationTwelveUsersPositiveTest() {
        //Регистрируем 12 пользаков, знаю что можно делать через рандомайзер, но что-то так захотелось
        Response response = apiRequests.createUser(userDataReg, token);
        verifyResponse(response, statusCode);

        //Коллекционируем что нужно для удаления и сортировки
        idNewUsers.add(response.body().path("_id"));
        forSort.add(response.body().path("name"));

        //Проверяем что данные по UserOne получаем
        userDataGetOne = new UserDataGetOne(response.body().path("email"));
        Response respAuthNewUser = apiRequests.getOneUser(userDataGetOne, token);
        verifyResponse(respAuthNewUser, SC_CREATED);


    }

    @AfterClass
    public static void tearDown() {

        //Сортируем (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧
        Collections.sort(forSort);
        System.out.println(forSort);

        //Удаляем
        for (int i = 0; i < idNewUsers.size(); i++) {
            Response respDelete = apiRequests.deleteUser(idNewUsers.get(i), token);
            respDelete.then().statusCode(SC_OK);
        }

        //Проверяем что нет пользаков.
        try {
            Response respCheckThatListOfUsersClear = apiRequests.getAllUsers(token);
            respCheckThatListOfUsersClear.then().statusCode(SC_OK);
            respCheckThatListOfUsersClear.then().assertThat().body(equalTo("[]"));
        } catch (AssertionError e) {
            System.out.println("Есть пользователи которые не были задействованы в тесте");
            e.printStackTrace();
        }
    }
}
