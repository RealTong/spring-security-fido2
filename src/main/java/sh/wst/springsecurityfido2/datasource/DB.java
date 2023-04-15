package sh.wst.springsecurityfido2.datasource;

import sh.wst.springsecurityfido2.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class DB {
    // 内存数据库方便测试
    private static final List<User> users = new ArrayList<User>();

    public static List<User> getUsers() {
        return users;
    }

    public static void setUser(User user) {
        DB.users.add(user);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User checkPassword(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}
