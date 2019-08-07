package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 朱广超
 * @Date: 2019/07/29/11:10
 * @Description:
 */

public class SecurityUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        ArrayList<GrantedAuthority> authorities = new ArrayList();
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ADMIN");
        authorities.add(sga);
        sga = new SimpleGrantedAuthority("add");
        authorities.add(sga);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        return userDetails;
    }
    private User findByUsername(String username){
        if("admin".equals(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword("$2a$10$rkWoqw6KZb2zbALnmxaeh.2iRU4k.xsnRXlYmMSCTM6kR7PMpe0RC");
            return user;
        }
        return null;
    }
}
