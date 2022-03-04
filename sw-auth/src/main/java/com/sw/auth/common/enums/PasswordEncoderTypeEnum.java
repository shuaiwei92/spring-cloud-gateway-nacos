package com.sw.auth.common.enums;

import lombok.Getter;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:27
 * @desc 描述
 **/
public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }

}
