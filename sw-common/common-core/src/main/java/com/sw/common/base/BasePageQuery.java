package com.sw.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 15:56
 * @desc 基础分页请求对象
 **/
@Data
public class BasePageQuery {

    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private int pageSize = 10;

}
