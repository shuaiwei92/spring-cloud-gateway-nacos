package com.sw.auth.security.core.userdetails.user;

import cn.hutool.core.collection.CollectionUtil;
import com.sw.system.dto.UserAuthDTO;
import com.sw.auth.common.enums.PasswordEncoderTypeEnum;
import com.sw.common.constant.GlobalConstants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author shuaiwei
 * @Date 2022/3/3 19:35
 * @desc 系统管理用户认证信息
 **/
@Data
public class SysUserDetails implements UserDetails {

    /**
     * 扩展字段
     */
    private Long userId;
    private String authenticationMethod;

    /**
     * 默认字段
     */
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 系统管理用户
     */
    public SysUserDetails(UserAuthDTO user) {
        this.setUserId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setPassword(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(user.getStatus()));
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
