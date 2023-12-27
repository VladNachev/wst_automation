package api.users;

import api.dto.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.Constants;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static io.restassured.mapper.ObjectMapperType.GSON;

@Feature("Create & Delete users")
@Tag("users")
public class CreateDeleteUserTest {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();
    private static String tempId;
    @BeforeAll
    public static void beforeAll(){
        // Generate token if needed
    }

    @Test
    @DisplayName("Create new user")
    @Description("Creation of new users is possible by running the POST create request")
    public void CreateUserTest(){
        // Define the DTO
        User Morpheus = User.builder()
                .name("Morpheus " + LocalDateTime.now())
                .job("Leader")
                .build();

        // Create user
        Response response = RestAssured.given()
                .log().all()
                .baseUri(Constants.BASE_API_URI)
                .basePath(Constants.BASE_PATH)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("User-Agent", "Mozilla")
                // .header("Authorization", "Bearer " + TOKEN) // Pass Bearer token if required
                // .auth().oauth2(TOKEN) // Another way to pass token
                .body(GSON.toJson(Morpheus))
                .when()
                .post(Constants.BASE_API_USERS_ENDPOINT)
                .prettyPeek();
        Assertions.assertEquals(201, response.statusCode()); // We are expecting statusCode 201

        // Extracting the ID value, so we can use it in the Delete user test
        tempId = response.then()
                .extract()
                .path("id");

        // Alternative way to extract the id value and save it into a local variable
        /*
        Integer itemId = response.jsonPath().get("id");
         */
    }
    @Test
    @DisplayName("Delete the recently created user")
    @Description("Deletion of user is possible via running the DELETE user request")
    public void DeleteUserTest(){
        Response deleteResponse = RestAssured.given()
                .log().all()
                .baseUri(Constants.BASE_API_URI)
                .basePath(Constants.BASE_PATH)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("User-Agent", "Mozilla")
                // .header("Authorization", "Bearer " + TOKEN) // Pass Bearer token if required
                // .auth().oauth2(TOKEN) // Another way to pass token
                .delete(Constants.BASE_API_USERS_ENDPOINT + "/" + tempId)
                .prettyPeek();
        Assertions.assertEquals(204, deleteResponse.statusCode()); // We are expecting statusCode 204
    }
}
