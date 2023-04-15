package sh.wst.springsecurityfido2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sh.wst.springsecurityfido2.datasource.DB;
import sh.wst.springsecurityfido2.pojo.Role;
import sh.wst.springsecurityfido2.pojo.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public void register(@RequestBody Map<String,String> map){
        String password = passwordEncoder.encode(map.get("password"));
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        Set set= new HashSet();
        set.add(role);
        User newUser = User.builder().username(map.get("username")).password(password).roles(set).build();

        DB.setUser(newUser);

    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        log.info("UserName: {}", username);
        log.info("Password: {}", password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return map;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestBody Map<String, String> map) {
        log.info("logout UserName: {}", map.get("username"));
        log.info("logout Password: {}", map.get("password"));
        return map;
    }

    @GetMapping("/test")
    public String test() {
        return "hello spring security";
    }
}
