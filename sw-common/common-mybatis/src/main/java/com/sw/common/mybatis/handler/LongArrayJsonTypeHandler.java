package com.sw.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * @Author shuaiwei
 * @Date 2022/3/4 14:24
 * @desc Long 数组类型转换 json
 **/
@Slf4j
@Component
@MappedTypes(value = {Long[].class})
@MappedJdbcTypes(value = {JdbcType.OTHER}, includeNullJdbcType = true)
public class LongArrayJsonTypeHandler extends ArrayObjectJsonTypeHandler<Long>{

    public LongArrayJsonTypeHandler() {
        super(Long[].class);
    }

}
