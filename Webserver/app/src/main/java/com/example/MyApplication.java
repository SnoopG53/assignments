package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class MyApplication {

    private static final String API_KEY = System.getenv("YOUTUBE_API_KEY");

    // Database connection details
    private static final String URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres?prepareThreshold=0";
    private static final String USER = "postgres.dyzwhxgtokbzefavnkbj";
    private static final String PASSWORD = "T8eHba3b5qBBDZfV";

    private final DSLContext dsl;

    public MyApplication() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            this.dsl = DSL.using(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @RequestMapping("/")
    public String home() {
        return "Welcome to the YouTube Search Application! Use the /news endpoint to search.";
    }

    @RequestMapping("/news")
    public String searchYoutube(@RequestParam(value = "topic", required = false, defaultValue = "trending") String topic) throws IOException {
        if (API_KEY == null || API_KEY.isBlank()) {
            return "<h1>Error: API Key is not configured. Please set the YOUTUBE_API_KEY environment variable.</h1>";
        }

        // Check if the query is already in the database
        String cachedResult = dsl.select(DSL.field("result", String.class))
                .from("youtube_cache")
                .where(DSL.field("query").eq(topic))
                .fetchOneInto(String.class);

        if (cachedResult != null) {
            // Convert cached JSON result to HTML format
            StringBuilder cachedHtml = new StringBuilder();
            cachedHtml.append("<h1>Cached YouTube Search Results for '").append(topic).append("'</h1>");

            // Parse JSON and generate HTML
            List<Map<String, String>> cachedVideos = new ObjectMapper().readValue(cachedResult, new TypeReference<>() {});
            for (Map<String, String> video : cachedVideos) {
                String title = video.get("title");
                String url = video.get("url");
                cachedHtml.append(String.format("<p><a href='%s'>%s</a></p>", url, title));
            }

            return cachedHtml.toString();
        }

        // Query YouTube API
        YouTube youtubeService = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> {}).setApplicationName("youtube-search").build();

        YouTube.Search.List request = youtubeService.search()
                .list("snippet")
                .setKey(API_KEY)
                .setQ(topic)
                .setType("video")
                .setMaxResults(10L);

        SearchListResponse response = request.execute();
        List<SearchResult> searchResults = response.getItems();

        if (searchResults.isEmpty()) {
            return "<h1>No results found for the topic: " + topic + "</h1>";
        }

        // Generate HTML and save results to the database
        List<Map<String, String>> videos = searchResults.stream()
                .map(result -> Map.of(
                        "title", result.getSnippet().getTitle(),
                        "url", "https://www.youtube.com/watch?v=" + result.getId().getVideoId()))
                .collect(Collectors.toList());

        String jsonResults = new ObjectMapper().writeValueAsString(videos);

        // Save to database
        dsl.insertInto(DSL.table("youtube_cache"))
                .set(DSL.field("query"), topic)
                .set(DSL.field("result", String.class), DSL.field("CAST(? AS JSONB)", String.class, jsonResults))
                .set(DSL.field("created_at"), DSL.currentTimestamp())
                .execute();

        // Convert to HTML format
        StringBuilder resultHtml = new StringBuilder();
        resultHtml.append("<h1>YouTube Search Results for '").append(topic).append("'</h1>");
        for (Map<String, String> video : videos) {
            String title = video.get("title");
            String url = video.get("url");
            resultHtml.append(String.format("<p><a href='%s'>%s</a></p>", url, title));
        }

        return resultHtml.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
