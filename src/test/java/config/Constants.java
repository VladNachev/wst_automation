package config;

public interface Constants {
    String BASE_PATH = System.getProperty("basePath", "/api");
    String BASE_API_URI = System.getProperty("baseAPIUri", "https://reqres.in");
    String BASE_API_USERS_ENDPOINT = System.getProperty("usersEndpoint", "/users");

}
