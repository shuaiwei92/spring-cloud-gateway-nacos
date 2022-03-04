package com.sw.gateway.kaptcha.router;

import com.sw.gateway.kaptcha.handler.CaptchaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 20:12
 * @desc 图片验证码路由
 **/
@Configuration
public class CaptchaRouter {

    @Bean
    public RouterFunction<ServerResponse> routeFunction(CaptchaHandler captchaHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/captcha")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), captchaHandler::handle);
    }

}
