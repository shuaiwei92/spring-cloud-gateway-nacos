package com.sw.common.redis.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 15:11
 * @desc redisson 连接配置类
 **/
@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

    private Integer database;

    public Integer getDatabase() {
        if (null == database) {
            return 0;
        }
        return database;
    }
}
