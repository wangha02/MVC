package rikkei.academy.service.User;

import rikkei.academy.config.Config;
import rikkei.academy.model.User;

import java.util.List;

public class UserServiceIMPL implements IUserService {
    public static String PATH_USER = "C:\\Users\\Admin\\IdeaProjects\\TinhKetQuaFizzBuzz\\untitled2\\untitled18\\src\\rikkei\\academy\\datebase\\user.txt";
    public static List<User> userList = new Config<User>().readFile(PATH_USER);

    @Override
    public List<User> findAll() {
        new Config<User>().writeFile(PATH_USER, userList);
        return userList;
    }

    @Override
    public void save(User user) {
        userList.add(user);
    }

    @Override
    public boolean existedByUsername(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (username.equals(userList.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existedByEmail(String email) {
        for (int i = 0; i < userList.size(); i++) {
            if (email.equals(userList.get(i).getEmail())) {
                return true;
            }
        }
        return false;
    }

    // neeus ton tai trong file thi return true
    @Override
    public boolean checkLogin(String username, String password) {
        for (int i = 0; i < userList.size(); i++) {
            if (username.equals(userList.get(i).getUsername()) && password.equals(userList.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (username.equals(userList.get(i).getUsername())) {
                return userList.get(i);
            }
        }
        return null;
    }

    @Override
    public User getCurrentUser() {
        if (new Config<User>().readFile(Config.PATH_USER_PRINCIPAL)!= null) {
            User user = new Config<User>().readFile(Config.PATH_USER_PRINCIPAL).get(0); // chấm gét 0 bởi vì chỉ có đúng 1 phần tử
            return user;
        }
        return null;
    }
}