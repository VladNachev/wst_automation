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

@Feature("Patch user")
@Tag("users")
public class CreatePatchUser {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();
    private static String tempId;
    @BeforeAll
    public static void beforeAll(){
        // Generate token if needed
    }

    @Test
    @DisplayName("Create and Patch user")
    @Description("Patching user is possible via running the PATCH request")
    public void CreatePatchUser(){
        // Firstly, we need to create the user, so we can patch it afterward
        // Define the DTO
        User Morpheus = User.builder()
                .name("Morpheus " + LocalDateTime.now())
                .job("Leader")
                .build();
        // Create the user
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
        // Extracting the ID value, so we can use it in the Patch user validation
        tempId = response.then()
                .extract()
                .path("id");
        // Alternative way to extract the id value and save it into a local variable
        /*
        Integer itemId = response.jsonPath().get("id");
         */
        // Changing the user's job in the DTO
        Morpheus.setJob("Zion president");
        // Set name to null
        // name will be excluded from the request body
        Morpheus.setName(null);
        // Patch the user
        Response patchResponse = RestAssured.given()
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
                .put(Constants.BASE_API_USERS_ENDPOINT + "/" + tempId)
                .prettyPeek();
        Assertions.assertEquals(200, patchResponse.statusCode()); // We are expecting statusCode 200
    }
}
