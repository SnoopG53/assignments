package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.json.jackson2.JacksonFactory;


import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RestController
public class MyApplication {
    @RequestMapping("/")
    public String home() {
        return "Welcome to the YouTube Search Application! Use the /news endpoint to search.";
    }

    private static final String API_KEY = "AIzaSyDjeNvO36OsnzfgprORWRb6GZb2bleGpP0";

    @RequestMapping("/news")
    public String searchYoutube(@RequestParam("topic") String topic) throws IOException {
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

        StringBuilder resultHtml = new StringBuilder("<h1>YouTube Search Results</h1>");
        for (SearchResult searchResult : searchResults) {
            String title = searchResult.getSnippet().getTitle();
            String videoId = searchResult.getId().getVideoId();
            String url = "https://www.youtube.com/watch?v=" + videoId;

            resultHtml.append(String.format("<p><a href='%s'>%s</a></p>", url, title));
        }

        System.out.println("API Key: " + API_KEY);
        System.out.println("Requesting YouTube API with topic: " + topic);
        return resultHtml.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
