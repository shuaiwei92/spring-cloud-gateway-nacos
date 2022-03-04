package com.sw.system.api;

import com.sw.system.dto.OAuth2ClientDTO;
import com.sw.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:24
 * @desc 描述
 **/
@FeignClient(value = "sw-system", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/api/v1/oauth-clients/getOAuth2ClientById")
    Result<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam String clientId);

}
