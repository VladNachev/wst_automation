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

@Feature("Register user")
@Tag("registration")
public class RegisterUserTest {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    @BeforeAll
    public static void beforeAll(){
        // Generate token if needed
    }

    @Test
    @DisplayName("Register new user")
    @Description("Registration of new user is possible")
    public void RegisterUserTest(){
        // Define the DTO
        RegistrationUser Eve = RegistrationUser.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        // Register user
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
        Assertions.assertEquals(200, response.statusCode()); // We are expecting statusCode 200

        // JSON response parsing
        JsonPath jsonPath = response.jsonPath();

        // Assert that the "id" and "token" fields are not null
        Assertions.assertNotNull(jsonPath.getInt("id"), "ID is null");
        Assertions.assertNotNull(jsonPath.getString("token"), "Token is null");


    }
}
