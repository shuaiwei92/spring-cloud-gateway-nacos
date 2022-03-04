package com.sw.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.common.constant.GlobalConstants;
import com.sw.common.result.Result;
import com.sw.system.dto.UserAuthDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author shuaiwei
 * @Date 2022/3/4 15:24
 * @desc 描述
 **/
@Slf4j
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

//    private final ISysUserService iSysUserService;
//    private final ISysUserRoleService iSysUserRoleService;
//    private final PasswordEncoder passwordEncoder;
//    private final ISysPermissionService iSysPermissionService;
//
//    @ApiOperation(value = "列表分页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "nickname", value = "用户昵称", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType = "Long"),
//    })
//    @GetMapping
//    public Result list(Integer page, Integer limit, String nickname, String mobile, Integer status, Long deptId) {
//
//        SysUser user = new SysUser();
//        user.setNickname(nickname);
//        user.setMobile(mobile);
//        user.setStatus(status);
//        user.setDeptId(deptId);
//
//        IPage<SysUser> result = iSysUserService.list(new Page<>(page, limit), user);
//        return Result.success(result.getRecords(), result.getTotal());
//    }
//
//    @ApiOperation(value = "用户详情")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
//    @GetMapping("/{id}")
//    public Result detail(@PathVariable Long id) {
//        SysUser user = iSysUserService.getById(id);
//        if (user != null) {
//            List<Long> roleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
//                    .eq(SysUserRole::getUserId, user.getId())
//                    .select(SysUserRole::getRoleId)
//            ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
//            user.setRoleIds(roleIds);
//        }
//        return Result.success(user);
//    }
//
//    @ApiOperation(value = "新增用户")
//    @PostMapping
//    public Result add(@RequestBody SysUser user) {
//        boolean result = iSysUserService.saveUser(user);
//        return Result.judge(result);
//    }
//
//    @ApiOperation(value = "修改用户")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
//    @PutMapping(value = "/{id}")
//    public Result update(
//            @PathVariable Long id,
//            @RequestBody SysUser user) {
//        boolean result = iSysUserService.updateUser(user);
//        return Result.judge(result);
//    }
//
//    @ApiOperation(value = "删除用户")
//    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
//    @DeleteMapping("/{ids}")
//    public Result delete(@PathVariable String ids) {
//        boolean status = iSysUserService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
//        return Result.judge(status);
//    }
//
//    @ApiOperation(value = "选择性更新用户")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
//    @PatchMapping(value = "/{id}")
//    public Result patch(@PathVariable Long id, @RequestBody SysUser user) {
//        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>().eq(SysUser::getId, id);
//        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
//        updateWrapper.set(user.getPassword() != null, SysUser::getPassword, passwordEncoder.encode(user.getPassword()));
//        boolean status = iSysUserService.update(updateWrapper);
//        return Result.judge(status);
//    }


    /**
     * 提供用于用户登录认证信息
     */
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
    @GetMapping("/username/{username}")
    public Result<UserAuthDTO> getUserByUsername(@PathVariable String username) {
//        UserAuthDTO user = iSysUserService.getByUsername(username);
        UserAuthDTO user = new UserAuthDTO();
        user.setUserId(1L);
        user.setUsername("admin");
        user.setPassword("$2a$10$yJSqqr6sTxNuYtA6EKcVUe2I4USFCzJ29sNcRrBvtAkSYcNg5ydQ6");
        user.setStatus(GlobalConstants.STATUS_YES);
        return Result.success(user);
    }


//    @ApiOperation(value = "获取当前登陆的用户信息")
//    @GetMapping("/me")
//    public Result<UserVO> getCurrentUser() {
//        UserVO userVO = new UserVO();
//        // 用户基本信息
//        Long userId = JwtUtils.getUserId();
//        SysUser user = iSysUserService.getById(userId);
//        BeanUtil.copyProperties(user, userVO);
//        // 用户角色信息
//        List<String> roles = JwtUtils.getRoles();
//        userVO.setRoles(roles);
//        // 用户按钮权限信息
//        List<String> perms = iSysPermissionService.listBtnPermByRoles(roles);
//        userVO.setPerms(perms);
//        return Result.success(userVO);
//    }

}
