package sh.wst.springsecurityfido2.service;

import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import sh.wst.springsecurityfido2.pojo.User;

import java.util.List;

public interface UserService extends UserDetailsService, UserDetailsPasswordService {
    List<User> getUsers();

    User save(User user);

}
