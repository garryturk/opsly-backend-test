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

@SpringBootTest
@AutoConfigureMockMvc
public class SocialControllerTests{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void emptyStringShouldReturnErrorCase(){
        int res = SocialController.getRecordCount("");
        assertEquals(res, -1);
    }

    @Test
    public void nullStringShouldReturnErrorCase(){
        int res = SocialController.getRecordCount(null);
        assertEquals(res, -1);
    }

    @Test
    public void emptyObjectShouldReturnErrorCase(){
        int res = SocialController.getRecordCount("{}");
        assertEquals(res, -1);
    }

    @Test
    public void emptyArrayShouldReturnZero(){
        int res = SocialController.getRecordCount("[]");
        assertEquals(res, 0);
    }

    @Test
    public void validArraysShouldReturnCorrectValues(){
        int res = SocialController.getRecordCount("[1]");
        assertEquals(res, 1);
        res = SocialController.getRecordCount("[1,2]");
        assertEquals(res, 2);
        res = SocialController.getRecordCount("[1,2,3]");
        assertEquals(res, 3);
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
