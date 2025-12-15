package ru.netology.tests;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class DataGenerator {

    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 9999;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
    }

    public static UserData createUser(String status) {
        String login = generateLogin();
        String password = generatePassword();
        sendRequest(login, password, status);
        return new UserData(login, password);
    }

    private static String generateLogin() {
        return "login_" + System.currentTimeMillis();
    }

    private static String generatePassword() {
        return "pass_" + System.currentTimeMillis();
    }

    private static void sendRequest(String login, String password, String status) {
        Gson gson = new Gson();
        UserPojo user = new UserPojo(login, password, status);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(user))
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static class UserData {
        private final String login;
        private final String password;

        public UserData(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class UserPojo {
        private final String login;
        private final String password;
        private final String status;

        public UserPojo(String login, String password, String status) {
            this.login = login;
            this.password = password;
            this.status = status;
        }
    }
}