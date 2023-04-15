package sh.wst.springsecurityfido2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sh.wst.springsecurityfido2.datasource.DB;
import sh.wst.springsecurityfido2.pojo.User;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: {}", username);
        User loginUser = DB.getUserByUsername(username);
        if (loginUser == null) {
            log.info("User {} not found", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        Set<GrantedAuthority> authorities = loginUser.getRoles()
                .stream()
                .map(role -> (GrantedAuthority) role::getName).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(username, loginUser.getPassword(), authorities);
    }
}
