package com.sw.auth;

import com.sw.system.api.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:45
 * @desc 描述
 **/
//@EnableFeignClients(basePackageClasses = {UserFeignClient.class, MemberFeignClient.class})
@EnableFeignClients(basePackageClasses = {UserFeignClient.class})
@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
