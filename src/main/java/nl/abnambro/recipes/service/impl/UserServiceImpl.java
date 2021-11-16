package nl.abnambro.recipes.service.impl;

import nl.abnambro.recipes.data.UserModel;
import nl.abnambro.recipes.repository.CustomUserRepository;
import nl.abnambro.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomUserRepository userRepository;

    @Override
    public UserModel currentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public UserModel findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
