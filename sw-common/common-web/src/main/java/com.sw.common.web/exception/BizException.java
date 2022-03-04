package com.sw.common.web.exception;

import com.sw.common.result.IResultCode;
import lombok.Getter;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 17:21
 * @desc 描述
 **/
@Getter
public class BizException extends RuntimeException {

    public IResultCode resultCode;

    public BizException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable cause){
        super(message, cause);
    }

    public BizException(Throwable cause){
        super(cause);
    }

}
