package com.pd.spring.redis.builder;

import com.pd.spring.redis.adaptor.JedisClusterAdaptor;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author peramdy on 2018/2/7.
 */
public class JedisClusterBuilder {

    /**
     * 集群地址
     */
    private String addressConfigs;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 最大链接
     */
    private Integer maxTotal;

    /**
     * 连接池
     */
    private GenericObjectPoolConfig genericObjectPoolConfig;

    public static JedisClusterBuilder getInstance() {
        return new JedisClusterBuilder();
    }

    @Ignore
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");


    public void setAddressConfigs(String addressConfigs) {
        this.addressConfigs = addressConfigs;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }

    /**
     * build JidesCluster
     *
     * @return
     * @throws Exception
     */
    public JedisCluster build() throws Exception {
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }
        if (maxTotal <= 0) {
            throw new IllegalArgumentException("maxTotal <= 0");
        }
        if (genericObjectPoolConfig == null) {
            throw new IllegalArgumentException("PoolConfig is null");
        }
        if (addressConfigs == null) {
            throw new IllegalArgumentException("未配置redis集群ip地址！");
        }
        JedisCluster jedisCluster = new JedisClusterAdaptor(parseHostAndPort(), timeout, maxTotal, genericObjectPoolConfig);

        return jedisCluster;
    }


    /**
     * parseHostAndPort
     *
     * @return
     */
    private Set<HostAndPort> parseHostAndPort() {
        try {
            if (StringUtils.isBlank(addressConfigs)) {
                throw new IllegalArgumentException("未配置redis集群ip地址！");
            }
            String[] address = addressConfigs.split(",");
            Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
            for (String ipPort : address) {
                boolean isIpPort = p.matcher(ipPort).matches();
                if (!isIpPort) {
                    throw new IllegalArgumentException("ip 或 port 不合法:" + ipPort);
                }
                String[] ipAndPort = ipPort.split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                hostAndPorts.add(hap);
            }
            return hostAndPorts;
        } catch (Exception ex) {
            throw new IllegalArgumentException("解析 jedis 配置文件失败", ex);
        }
    }
}