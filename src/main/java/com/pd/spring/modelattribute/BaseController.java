package com.pd.spring.modelattribute;

import com.pd.spring.exception.PdException;
import com.pd.spring.redis.util.JedisUtils;
import com.pd.spring.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pd 2018/3/9.
 */
public class BaseController {

    @Autowired
    private JedisUtils jedisUtils;

    @ModelAttribute("userId")
    public Integer getUserId(HttpServletRequest httpServletRequest) throws PdException {
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
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @ModelAttribute("ip")
    public String getClientIpAddress(HttpServletRequest request) {
        return IpUtil.parseIp(request);
    }


}
