package com.opsly.social;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.http.*;
import java.util.*;

@RestController
public class SocialController {

    @GetMapping("/")
    public Social getSocial() {

        return new Social(5, 8, 3);
    }
}
