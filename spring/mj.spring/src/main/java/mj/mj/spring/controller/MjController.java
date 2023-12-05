package mj.mj.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MjController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute( "data", "hello!!");
        return "hello";

    }

    @GetMapping("test-mvc")
    public String testMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "test-template";
    }

    @GetMapping("test-string")
    @ResponseBody
    public String testString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("test-api")
    @ResponseBody
    public Test testapi(@RequestParam("name") String name) {
        Test test = new Test();
        test.setName(name);
        return test;
    }

    static class Test {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


