package com.pd.spring.test.controller;

import com.pd.spring.modelattribute.BaseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pd on 2018/3/9.
 */
@RestController
public class TestModelAttributeController extends BaseModel {


    @GetMapping("/userId")
    @ResponseBody
    public String userId(@ModelAttribute("userId") Integer userId) {
        System.out.println(userId);
        return "success";
    }
}
