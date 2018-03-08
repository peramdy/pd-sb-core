package com.pd.spring.test.service;

import com.pd.spring.redis.PdCache;
import com.pd.spring.redis.PdDelCache;
import org.springframework.stereotype.Service;

/**
 * @author peramdy on 2018/2/7.
 */
@Service
public class TestService {

    @PdCache(value = "#{id}", expire = 5)
    public String queryMsg(Integer id) {
        System.out.println("query db");
        return "pd-query";
    }

    @PdDelCache(value = "#{id}")
    public String delMsg(Integer id) {
        System.out.println("del db");
        return "pd-del";
    }
}
