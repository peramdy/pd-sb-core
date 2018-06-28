package com.pd.spring.test.service;

import com.pd.spring.redis.PdCache;
import com.pd.spring.redis.PdDelCache;
import com.pd.spring.test.model.Msg;
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

    @PdCache(value = "#{msgId}", expire = 5)
    public Msg msgInfo(Integer msgId) {
        Msg msg = new Msg();
        msg.setId(123);
        msg.setContent("hello world");
        System.out.println("QUERY MSG DB");
        return msg;
    }


    public String queryIP(String ip) {
        System.out.println(ip);
        return "ip";
    }


}
