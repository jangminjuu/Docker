package mj.mj.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // "/" : 첫번째 로컬호스트 8080으로 들어오면 해당 되는 게 호출 됨 (home.html)
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
