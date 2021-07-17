package com.info.patient.service;


import com.info.patient.enums.Role;
import com.info.patient.model.User;
import com.info.patient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public List<User> findAll() {
        return userRepository.findAll();
    }
    public List<User> findAllByRole(String userType) {
        return userRepository.findAllByRole(userType);
    }

    public boolean saveUser(User user) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findById(Long userId){
        return userRepository.findById(userId).get();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean isLoggedIn(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isLoggedIn = false;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            isLoggedIn = true;
        } else {
            String username = principal.toString();
            isLoggedIn = false;
        }
        return isLoggedIn;
    }

}
