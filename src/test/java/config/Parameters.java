package config;

public interface Parameters {
    int PAGE = Integer.parseInt(System.getProperty("page", "2"));
    int PER_PAGE = Integer.parseInt(System.getProperty("per_page", "3"));
}
