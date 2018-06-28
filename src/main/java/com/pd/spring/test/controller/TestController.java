package com.pd.spring.test.controller;


import com.pd.spring.ip.PdIP;
import com.pd.spring.test.model.Msg;
import com.pd.spring.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peramdy on 2018/2/7.
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/msg/{id}")
    public String queryMsg(@PathVariable("id") Integer id) {
        return testService.queryMsg(id);
    }

    @GetMapping("/delMsg/{id}")
    public String delMsg(@PathVariable("id") Integer id) {
        String str = testService.delMsg(id);
        return str;
    }

    @GetMapping("/ip")
    public String ip(String ip) {
        String str = testService.queryIP("1213");
        System.out.println(str);
        return str;
    }


    @GetMapping("/msg")
    public String msg(String ss) {
        Msg msg = testService.msgInfo(123);
        return msg.toString();
    }

    @GetMapping("/address")
    public String address(@PdIP String address) {
        System.out.println(address);
        return address;
    }

}
