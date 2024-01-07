package api.register;

import api.dto.RegistrationUser;
import api.dto.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.Constants;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static io.restassured.mapper.ObjectMapperType.GSON;

@Feature("Register user - Negative test")
@Tag("registration")
public class RegisterUserNoPasswordTest {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    @BeforeAll
    public static void beforeAll() {
        // Generate token if needed
    }

    @Test
    @DisplayName("Register user - Negative Test")
    @Description("Negative test for attempt of user registration without providing a password")
    public void RegisterUserNoPasswordTest() {
        // Define the DTO without password
        RegistrationUser Eve = RegistrationUser.builder()
                .email("eve.holt@reqres.in")
                .build();
        // Attempt to register user without providing a password
        Response response = RestAssured.given()
                .log().all()
                .baseUri(Constants.BASE_API_URI)
                .basePath(Constants.BASE_PATH)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("User-Agent", "Mozilla")
                // .header("Authorization", "Bearer " + TOKEN) // Pass Bearer token if required
                // .auth().oauth2(TOKEN) // Another way to pass token
                .body(GSON.toJson(Eve))
                .when()
                .post(Constants.BASE_API_REG_ENDPOINT)
                .prettyPeek();
        Assertions.assertEquals(400, response.statusCode()); // We are expecting statusCode 400 Bad request

        // JSON response parsing
        JsonPath jsonPath = response.jsonPath();

        // Assert that the "error" field is present and has the expected text
        Assertions.assertTrue(jsonPath.get("error") instanceof String, "Error field is not present or not a string");
        Assertions.assertEquals("Missing password", jsonPath.getString("error"), "Unexpected error message");

    }
}