package sh.wst.springsecurityfido2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 禁用csrf
        http.csrf().disable();
        // login和logout的请求不需要认证
        http.authorizeHttpRequests().requestMatchers("/register", "/login", "/logout").permitAll();
        // systemadmin角色可以访问/sys/**路径
        http.authorizeHttpRequests().requestMatchers("/sys/**").hasRole("systemadmin");
        // oiladmin角色可以访问/oil/**路径
        http.authorizeHttpRequests().requestMatchers("/oil/**").hasRole("oiladmin");
        // user角色可以访问/user/**路径
        http.authorizeHttpRequests().requestMatchers("/user/**").hasRole("user");
        // 所有请求都需要认证
        http.authorizeHttpRequests().anyRequest().denyAll();
        // 登录请求的url
//        http.formLogin().loginProcessingUrl("/login");
//        // 登录成功后的url
//        http.formLogin().successForwardUrl("/test");

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 自定义密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
