package hello.servlet.web.frontController.v4.controller;

import hello.servlet.web.frontController.v4.ControllerV4;

import java.util.Map;

public class MemberFromControllerV4 implements ControllerV4 {


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
