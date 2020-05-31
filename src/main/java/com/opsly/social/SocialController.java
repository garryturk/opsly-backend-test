package com.opsly.social;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static java.util.stream.Collectors.toList;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
public class SocialController {
    public static JSONArray getPosts(String str){
        JSONArray posts;

        try{
            posts = new JSONArray(str);
        } catch(org.json.JSONException|NullPointerException e) {posts = new JSONArray("[]");}

        return posts;
    }

    @GetMapping("/")
    public String getSocial() {
        HttpClient client = HttpClient.newHttpClient();
        List<URI> uris = new LinkedList<>();

        // In prod code there would be no empty catch blocks of course!
        try{
            uris.add(new URI("https://takehome.io/twitter"));
            uris.add(new URI("https://takehome.io/facebook"));
            uris.add(new URI("https://takehome.io/instagram"));
        } catch(URISyntaxException e){}

        // Make a list of requests
        List<HttpRequest> requests = new LinkedList<>();
        requests.add(HttpRequest.newBuilder(uris.get(0)).build());
        requests.add(HttpRequest.newBuilder(uris.get(1)).build());
        requests.add(HttpRequest.newBuilder(uris.get(2)).build());

        // Record overall execution time and log to console
        long start = System.currentTimeMillis();

        // For each URI create an async request and get the array of posts
        List<JSONArray> results = requests.stream().parallel()
            .map(request -> client.sendAsync(request, ofString())
                .thenApply(res -> res.body()))
            .map(result -> {
                String str;
                try{
                    str = result.get();
                }catch(Exception e) {str="";}

                return getPosts(str);
            })
            .collect(toList());

        long end = System.currentTimeMillis();
        System.out.println("Time: "+ (end-start));

        JSONObject json = new JSONObject();
        json.put("twitter", results.get(0));
        json.put("facebook", results.get(1));
        json.put("instagram", results.get(2));

        return json.toString();
    }
}
