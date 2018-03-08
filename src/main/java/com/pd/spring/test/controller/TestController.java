package com.pd.spring.test.controller;


import com.pd.spring.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peramdy on 2018/2/7.
 */
@RestController
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping("/msg/{id}")
    @ResponseBody
    public String queryMsg(@PathVariable("id") Integer id) {
        return testService.queryMsg(id);
    }

    @GetMapping("/delMsg/{id}")
    @ResponseBody
    public String delMsg(@PathVariable("id") Integer id) {
        String str = testService.delMsg(id);
        return str;
    }

}
