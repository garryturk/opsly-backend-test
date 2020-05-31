package com.opsly.social;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.equalTo;
import org.json.JSONArray;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialControllerTests{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void emptyStringShouldReturnEmptyArray(){
        JSONArray res = SocialController.getPosts("");
        assertEquals(res.length(), 0);
    }

    @Test
    public void nullStringShouldReturnEmptyArray(){
        JSONArray res = SocialController.getPosts("");
        assertEquals(res.length(), 0);
    }

    @Test
    public void emptyObjectShouldReturnEmptyArray(){
        JSONArray res = SocialController.getPosts("");
        assertEquals(res.length(), 0);
    }

    @Test
    public void emptyArrayShouldReturnEmptyArray(){
        JSONArray res = SocialController.getPosts("");
        assertEquals(res.length(), 0);
    }

    @Test
    public void validArraysShouldReturnCorrectValues(){
        JSONArray res = SocialController.getPosts("[{k1:1}]");
        assertEquals(res.length(), 1);
        res = SocialController.getPosts("[{k1:1}, {k2:2}]");
        assertEquals(res.length(), 2);
        res = SocialController.getPosts("[{k1:1}, {k2:2}, {k3:3}]");
        assertEquals(res.length(), 3);
    }

    @Test
    public void serviceCallShouldReturnExpectedJson() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasKey(equalTo("twitter"))))
            .andExpect(jsonPath("$", hasKey(equalTo("facebook"))))
            .andExpect(jsonPath("$", hasKey(equalTo("instagram"))));
    }

}
