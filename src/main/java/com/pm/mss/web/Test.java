package com.pm.mss.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("web")
public class Test {


    @RequestMapping(value = "/hello",method= RequestMethod.GET)
    public String hello(HttpServletRequest req, HttpServletResponse rsp){
        String s =new String();
        return "hello world";
        //测试

        //测试

        //测试

        //
    }
}
