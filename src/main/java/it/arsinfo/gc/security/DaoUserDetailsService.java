package it.arsinfo.gc.security;

import java.util.Collections;

import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.ui.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class DaoUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserInfoService service;

    private static final Logger log = LoggerFactory.getLogger(DaoUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserInfo user = service.findByUsername(username);
        if (null == user) {
            log.info("login: '{}' not found, access is denied.", username);
            throw new UsernameNotFoundException("No user found with username: "
                    + username);
        }
        log.info("login: {}",user);
        return new User(user.getUsername(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }

}