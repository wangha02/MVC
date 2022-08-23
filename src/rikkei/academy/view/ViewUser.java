package rikkei.academy.view;

import rikkei.academy.config.Config;
import rikkei.academy.controller.UserController;
import rikkei.academy.dto.request.SignUpDTO;
import rikkei.academy.dto.request.SignInDTO;
import rikkei.academy.dto.response.ResponseMessenger;
import rikkei.academy.model.Role;
import rikkei.academy.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ViewUser {
    UserController userController = new UserController();
    List<User> userList = userController.showUsersList();

    public ViewUser() {
    }

    public void formRegister() {
        // fix ID tự tăng
        System.out.println("***********FORM REGISTER***********");
        int id;
        if (userList.size() == 0) {
            id = 1;
        } else {
            id = userList.get(userList.size() - 1).getId() + 1;
        }

        //NAME
        String name;
        boolean validateName;
        while (true) {
            System.out.println("Enter the name");
            name = Config.scanner().nextLine();
            validateName = Pattern.matches("[A-Z][a-zA-Z[\\s]]{1,10}", name);
            if (validateName) {
                break;
            } else {
                System.out.println("The name failed! Please try again");
            }
        }

        //UserName
        String username;
        boolean validateusername;
        while (true) {
            System.out.println("Enter the username");
            username = Config.scanner().nextLine();
            validateusername = Pattern.matches("[a-zA-Z0-9]{1,40}", username); // không có khoảng trắng
            if (validateusername) {
                break;
            } else {
                System.out.println("The username failed! Please try again !");
            }
        }

        //EMAIL
        String email;
        boolean validateEmail;
        while (true) {
            System.out.println("Enter the email");
            email = Config.scanner().nextLine();
            validateEmail = Pattern.matches("^(.+)@(.+)$", email); //Tất cả các kí tự //"$": là kết thúc
            if (validateEmail) {
                break;
            } else {
                System.out.println("The email failed! Please try again");
            }
        }

        //PASSWORD
        String password;
        boolean validatePassword;
        while (true) {
            System.out.println("Enter the password");
            password = Config.scanner().nextLine();
            validatePassword = Pattern.matches("[a-zA-Z0-9]{1,40}", password); // không có khoảng trắng
            if (validatePassword) {
                break;
            } else {
                System.out.println("The password failed! Please try again");
            }
        }
        //DTO (Data transfer object): là các class đóng gói data để chuyển giữa client - server hoặc giữa các service trong microservice
        System.out.println("Enter the Role: ");
        String role = Config.scanner().nextLine();
        Set<String> strRole = new HashSet<>();
        strRole.add(role);
        SignUpDTO signUpDTO = new SignUpDTO(id, name, username, email, password, strRole);
        //Lấy đối tượng message từ Controller đổ về để check validate các trường hợp trùng lặp trong database
        ResponseMessenger check_existed = userController.register(signUpDTO);
        //IN RA MÀU CHO System.out -> màu vàng a e tìm hiểu thêm in màu khác nhé
        final String ANSI_RESET = "\\u001B[0m";
        final String ANSI_YELLOW = "\\u001B[33m";
        if (check_existed.getMessenger().equals("username_existed")) {
            System.out.println("The username is existed! Please try again! ");
            formRegister();
        } else if (check_existed.getMessenger().equals("email_existed")) {
            System.out.println("The email is existed! Please try again!");
            formRegister();
        } else if (check_existed.getMessenger().equals("success")) {
            System.out.println("CREATE USER SUCCESS!!!!!!!!!");
            System.out.println("CHECK LIST =>> " + userController.showUsersList());
        }
    }

    public void showListUser() {
        System.out.println("======ID========NAME================USERNAME================EMAIL================AVATAR=======================STATUS=====================ROLE===============");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println("====" + userList.get(i).getId() + "=====" + userList.get(i).getName() + "=====" + userList.get(i).getUsername() + "=====" + userList.get(i).getEmail() + "====="+ userList.get(i).getAvatar() + "=====" + userList.get(i).isStatus() + "=====" + userList.get(i).getRoles());

        }
    }

    public void formLogin() {
        System.out.println("================================FORM_LOGIN================================");

        //USERNAME
        String username;
        boolean validateusername;
        while (true) {
            System.out.println("Enter the username");
            username = Config.scanner().nextLine();
            validateusername = Pattern.matches("[a-zA-Z0-9]{1,40}", username); // không có khoảng trắng
            if (validateusername) {
                break;
            } else {
                System.out.println("The username failed! Please try again !");
            }
        }
        //PASSWORD
        String password;
        boolean validatePassword;
        while (true) {
            System.out.println("Enter the password");
            password = Config.scanner().nextLine();
            validatePassword = Pattern.matches("[a-zA-Z0-9]{1,40}", password); // không có khoảng trắng
            if (validatePassword) {
                break;
            } else {
                System.out.println("The password failed! Please try again");
            }
        }

        ResponseMessenger messenger = userController.login(new SignInDTO(username, password));
        if (messenger.getMessenger().equals("Login _ failed")) { // phải viết giống bên Controller
            System.out.println("Login failed! Please try again !");
            formLogin();
        }else {
            profile();
        }
    }

    public void profile(){
        System.out.println("===========YOUR PROFILE===========");
        User userLogin = userController.getCurrentUser();
        System.out.println("WELLCOME: " + userLogin.getName());
        String roleUser = null;
        Iterator<Role> iterator =userLogin.getRoles().iterator();
        while (iterator.hasNext()) {
            roleUser = String.valueOf(iterator.next().getName());
        }

            if (roleUser.equals("ADMIN")){
                System.out.println("1. CHỨC NĂNG DÀNH CHO ADMIN");
        }
        System.out.println("2. lOG OUT "); // OUT
        System.out.println("3. BACK MEMU"); // BACK MEMU
        int chooseMenu = Config.scanner().nextInt();
        switch (chooseMenu) {
            case 2:
                new Config<User>().writeFile(Config.PATH_USER_PRINCIPAL,null);
                new Main();
                break;
                case 3:
                    new Main();
                    break;

        }

    }
}

