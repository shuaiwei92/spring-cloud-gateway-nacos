package com.sw.system.api.fallback;

import com.sw.system.api.UserFeignClient;
import com.sw.common.result.Result;
import com.sw.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:32
 * @desc 描述
 **/
@Slf4j
@Component
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public Result getUserByUsername(String username) {
        log.error("feign远程调用系统用户服务异常后的降级方法");
        return Result.failed(ResultCode.DEGRADATION);
    }

}
