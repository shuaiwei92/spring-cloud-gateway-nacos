package com.sw.system.api;

import com.sw.system.api.fallback.UserFeignFallbackClient;
import com.sw.system.dto.UserAuthDTO;
import com.sw.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:31
 * @desc 描述
 **/
@FeignClient(value = "sw-system", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/username/{username}")
    Result<UserAuthDTO> getUserByUsername(@PathVariable String username);

}
