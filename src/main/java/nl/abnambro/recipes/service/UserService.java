package nl.abnambro.recipes.service;

import nl.abnambro.recipes.data.UserModel;

public interface UserService {

    UserModel currentUser();

    UserModel findUserByUserName(String username);
}
