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
    private static int getRecordCount(CompletableFuture<String> str){
        int count = 0;

        try{
            JSONArray records = new JSONArray(str.get());
            count = records.length();
        } catch(org.json.JSONException|InterruptedException|ExecutionException e) {count = -1;}

        return count;
    }

    @GetMapping("/")
    public Social getSocial() {
        HttpClient client = HttpClient.newHttpClient();
         List<URI> uris = new LinkedList<>();
        try{
            uris.add(new URI("https://takehome.io/twitter"));
            uris.add(new URI("https://takehome.io/facebook"));
            uris.add(new URI("https://takehome.io/facebook"));
        } catch(URISyntaxException e){}

        List<HttpRequest> requests = uris.stream()
            .map(HttpRequest::newBuilder)
            .map(reqBuilder -> reqBuilder.build())
            .collect(toList());

        long start = System.currentTimeMillis();
        List<Integer> results = requests.stream().parallel()
            .map(request -> client.sendAsync(request, ofString())
                .thenApply(res -> res.body()))
            .map(result -> getRecordCount(result))
            .collect(toList());

        long end = System.currentTimeMillis();
        System.out.println("Time: "+ (end-start));

        int twitter = results.get(0);
        int facebook = results.get(1);
        int instagram = results.get(2);

        return new Social(twitter, facebook, instagram);
    }
}
