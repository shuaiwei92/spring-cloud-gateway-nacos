package com.sw.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:33
 * @desc 角色权限信息
 **/
@Data
@Accessors(chain = true)
public class RolePermissionDTO {

    private Long roleId;
    private List<Long> permissionIds;
    private Long menuId;

}
