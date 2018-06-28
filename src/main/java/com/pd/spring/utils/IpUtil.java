package com.pd.spring.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author peramdy on 2018/6/28.
 */
public class IpUtil {


    /**
     * ipv4 形式
     */
    private static final String LOCAL_IP_V4 = "127.0.0.1";

    /**
     * ipv6 形式
     */
    private static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";


    /**
     * ip header 名称
     */
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

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String parseIp(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String currentIp = request.getHeader(header);
            if (currentIp != null && currentIp.length() != 0 && !"unknown".equalsIgnoreCase(currentIp)) {
                return currentIp;
            }
        }
        String ipAddress = request.getRemoteAddr();
        if (ipAddress.equals(LOCAL_IP_V4) || ipAddress.equals(LOCAL_IP_V6)) {
            //根据网卡取本机配置的IP
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ipAddress = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ipAddress;
    }


}
