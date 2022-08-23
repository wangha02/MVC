package rikkei.academy.controller;

import rikkei.academy.config.Config;
import rikkei.academy.dto.request.SignUpDTO;
import rikkei.academy.dto.request.SignInDTO;
import rikkei.academy.dto.response.ResponseMessenger;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.service.Role.IRoleService;
import rikkei.academy.service.Role.RoleServiceIMPL;
import rikkei.academy.service.User.IUserService;
import rikkei.academy.service.User.UserServiceIMPL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {
    private IUserService uerService = new UserServiceIMPL();
    private IRoleService roleService = new RoleServiceIMPL();

    public List<User> showUsersList() {
        return uerService.findAll();
    }

    public ResponseMessenger register(SignUpDTO signUpDTO) {
        if (uerService.existedByUsername(signUpDTO.getUsername())) {
            return new ResponseMessenger("username_existed");
        }
        if (uerService.existedByEmail(signUpDTO.getEmail())) {
            return new ResponseMessenger("email_existed");
        }
        Set<String> strRoles = signUpDTO.getStrRoles();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN);
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM);
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER);
                    roles.add(userRole);
            }
        });
        User user = new User(signUpDTO.getId(), signUpDTO.getName(), signUpDTO.getUsername(), signUpDTO.getEmail(), signUpDTO.getPassword(), roles);
        uerService.save(user);
        showUsersList();
        return new ResponseMessenger ("success");
    }
    public ResponseMessenger login(SignInDTO signUpDTO) {
        if (uerService.checkLogin(signUpDTO.getUsername(), signUpDTO.getPassword())){
            User user = uerService.findByUsername(signUpDTO.getUsername());
            List<User> usersLogin = new ArrayList<>();
            usersLogin.add(user);
            new Config<User>().writeFile(Config.PATH_USER_PRINCIPAL,usersLogin);
            return new ResponseMessenger("Login _ success");
        }else {
            return new ResponseMessenger("Login _ failed"); // phải viết giống bên viewUser
        }
    }
    public User getCurrentUser() {
        return uerService.getCurrentUser();
    }

}
