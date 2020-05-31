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

@RestController
public class SocialController {
    public static int getRecordCount(String str){
        int count = 0;

        // If the string is not well-formed JSON set an error value of -1
        try{
            JSONArray records = new JSONArray(str);
            count = records.length();
        } catch(org.json.JSONException|NullPointerException e) {count = -1;}

        return count;
    }

    @GetMapping("/")
    public Social getSocial() {
        HttpClient client = HttpClient.newHttpClient();
        List<URI> uris = new LinkedList<>();

        // In prod code there would be no empty catch blocks of course!
        try{
            uris.add(new URI("https://takehome.io/twitter"));
            uris.add(new URI("https://takehome.io/facebook"));
            uris.add(new URI("https://takehome.io/instagram"));
        } catch(URISyntaxException e){}

        // Make an list of requests
        List<HttpRequest> requests = new LinkedList<>();
        requests.add(HttpRequest.newBuilder(uris.get(0)).build());
        requests.add(HttpRequest.newBuilder(uris.get(1)).build());
        requests.add(HttpRequest.newBuilder(uris.get(2)).build());

        // Record overall execution time and log to console
        long start = System.currentTimeMillis();

        // For each URI create an async request and get the array of posts
        List<String> results = requests.stream().parallel()
            .map(request -> client.sendAsync(request, ofString())
                .thenApply(res -> res.body()))
            .map(result -> {
                String str;
                try{
                    str = result.get();
                }catch(Exception e) {str="";}
                int count = getRecordCount(str);

                if(count == -1){
                // Error case, return empty array
                    return "[]";
                } else {
                    return str;
                }

            })
            .collect(toList());

        long end = System.currentTimeMillis();
        System.out.println("Time: "+ (end-start));

        return new Social(results.get(0), results.get(1), results.get(2));
    }
}
