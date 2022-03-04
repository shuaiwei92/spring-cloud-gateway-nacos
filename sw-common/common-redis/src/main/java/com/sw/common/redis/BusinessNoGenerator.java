package com.sw.common.redis;

import com.sw.common.constant.RedisConstants;
import com.sw.common.enums.BusinessTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 15:15
 * @desc 描述
 **/
@Slf4j
@Component
public class BusinessNoGenerator {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param businessType 业务类型枚举
     * @param digit        业务序号位数
     * @return
     */
    public String generate(BusinessTypeEnum businessType, Integer digit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = RedisConstants.BUSINESS_NO_PREFIX +businessType.getCode() + ":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);
        return date + businessType.getValue() + String.format("%0" + digit + "d", increment);
    }


    public String generate(BusinessTypeEnum businessType) {
        Integer defaultDigit = 6;
        return generate(businessType, defaultDigit);
    }


}
