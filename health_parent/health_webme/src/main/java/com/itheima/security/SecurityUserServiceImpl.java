package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/31/20:39
 * @Description:
 */
@Component("securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (null == user) {
            return null;
        }
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList();
        SimpleGrantedAuthority sga = null;
        if (null != roles) {
            for (Role role : roles) {
                //授予角色
                sga = new SimpleGrantedAuthority(role.getKeyword());
                authorityList.add(sga);
                //角色下的权限
                Set<Permission> permissions = role.getPermissions();
                if (null != permissions) {
                    //添加权限
                    for (Permission per : permissions) {
                        sga = new SimpleGrantedAuthority(per.getKeyword());
                        authorityList.add(sga);
                    }
                }
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
        return userDetails;
    }
}
