package com.pd.spring.test.controller;

import com.pd.spring.modelattribute.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pd on 2018/3/9.
 */
@RestController
public class ModelAttributeController extends BaseController {


    @GetMapping("/userId")
    @ResponseBody
    public String userId(@ModelAttribute("userId") Integer userId) {
        System.out.println(userId);
        return "success";
    }


    @GetMapping("/getIp")
    @ResponseBody
    public String ip(@ModelAttribute("ip") String ip) {
        System.out.println(ip);
        return ip;
    }
}
