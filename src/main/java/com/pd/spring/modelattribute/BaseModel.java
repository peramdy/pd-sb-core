package com.pd.spring.modelattribute;

import com.pd.spring.redis.util.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pd 2018/3/9.
 */
public class BaseModel {

    @Autowired
    private JedisUtils jedisUtils;

    @ModelAttribute("userId")
    public Integer getUserId(HttpServletRequest httpServletRequest) {
        Jedis jedis = null;
        try {
            String token = httpServletRequest.getHeader("token");
            if (StringUtils.isBlank(token)) {
                return null;
            }
            jedis = jedisUtils.createJedis();
            String value = jedis.get(token);
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return Integer.getInteger(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    @ModelAttribute("ip")
    public String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }


}
