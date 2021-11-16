package nl.abnambro.recipes.repository;


import nl.abnambro.recipes.data.UserModel;


public interface CustomUserRepository {

    UserModel findByUsername(String username);

}