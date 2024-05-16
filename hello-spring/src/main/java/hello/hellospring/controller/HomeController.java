package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") // localhost:8090 접속 시
    public String home() {
        return "home"; // home.html 이동
    }
}
