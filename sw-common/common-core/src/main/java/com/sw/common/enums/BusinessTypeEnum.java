package com.sw.common.enums;

import lombok.Getter;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 15:53
 * @desc 业务类型枚举
 **/
public enum BusinessTypeEnum {

    USER("user", 100),
    MEMBER("member", 200),
    ORDER("order", 300);

    @Getter
    private String code;

    @Getter
    private Integer value;

    BusinessTypeEnum(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public static BusinessTypeEnum getValue(String code) {
        BusinessTypeEnum businessTypeEnum=null;
        for (BusinessTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                businessTypeEnum =value;
                continue;
            }
        }
        return businessTypeEnum;
    }

}
