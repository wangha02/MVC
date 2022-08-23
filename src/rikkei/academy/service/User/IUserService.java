package rikkei.academy.service.User;

import rikkei.academy.model.User;
import rikkei.academy.service.IGenericService;

public interface IUserService extends IGenericService<User> {
    //check trung` lap name//email
    boolean existedByUsername(String username);
    boolean existedByEmail(String email);
    boolean checkLogin(String username, String password);
    User findByUsername(String username);
    User getCurrentUser();

}
