package api.users;

import config.Constants;
import config.Parameters;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.hasSize;

@Feature("Get Users")
@Tag("users")
public class ListUsersTest {
    @BeforeAll
    public static void beforeAll(){
        // Generate token if needed
    }

    @Test
    @DisplayName("Can get all users")
    @Description("API user can retrieve a list of all registered users by running the GET users request")
    public void ListUsersTests(){
        RestAssured.given()
                .log().all()
                .baseUri(Constants.BASE_API_URI)
                .basePath(Constants.BASE_PATH)
                .contentType(ContentType.JSON)
                .header("User-Agent", "Mozilla")
                // .header("Authorization", "Bearer " + TOKEN) // Pass Bearer token if required
                // .auth().oauth2(TOKEN) // Another way to pass token
                .when()
                .get(Constants.BASE_API_USERS_ENDPOINT)
                .then()
                .log().all()
                // Check if status code is 200
                .statusCode(200)
                // If the page parameter is not specified, then 1st page should be always listed
                .body("page", CoreMatchers.equalTo(1))
                // Check if the expected string is presented with the payload
                .body("support.url", CoreMatchers.equalTo("https://reqres.in/#support-heading"));
    }

    @Test
    @DisplayName("Can list a certain page")
    @Description("API user can list a certain page of registered users and filter the results by per_page")
    public void ListUserSpecifyPageTest(){
        RestAssured.given()
                .log().all()
                .baseUri(Constants.BASE_API_URI)
                .basePath(Constants.BASE_PATH)
                .contentType(ContentType.JSON)
                .header("User-Agent", "Mozilla")
                // .header("Authorization", "Bearer " + TOKEN) // Pass Bearer token if required
                // .auth().oauth2(TOKEN) // Another way to pass token
                .queryParam("page", Parameters.PAGE)
                .queryParam("per_page", Parameters.PER_PAGE)
                .when()
                .get(Constants.BASE_API_USERS_ENDPOINT)
                .then()
                .log().all()
                // Check if status code is 200
                .statusCode(200)
                // Check if the specified page is listed
                .body("page", CoreMatchers.equalTo(Parameters.PAGE))
                // Check if the amount of users per page is correct
                .body("data", hasSize(3));
    }
}
