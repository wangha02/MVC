package rikkei.academy.view;

import rikkei.academy.config.Config;
import rikkei.academy.controller.UserController;
import rikkei.academy.model.User;
import rikkei.academy.service.Role.RoleServiceIMPL;

public class Main {
    public Main() {
        UserController userController = new UserController(); // check đăng kí đăng nhập
        User user = userController.getCurrentUser();
        new RoleServiceIMPL().findAll();
        System.out.println(new RoleServiceIMPL().findAll());
        if (user == null) {
            System.out.println("1. Register");
            System.out.println("2. Login");
        }else {
            System.out.println("3. Show List User");
            System.out.println("4. My Profile");
            System.out.println("5. Create Category");
            System.out.println("6. Show List Category");
        }

        int chooseMenu = Config.scanner().nextInt();
        switch (chooseMenu) {
            case 1:
                new ViewUser().formRegister();
                break;
            case 2:
                new ViewUser().formLogin();
                break;
            case 3:
                new ViewUser().showListUser();
                break;
            case 4:
                new ViewUser().profile();
                break;
            case 5:
                new ViewCategory().formCreateCategory();
                break;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
