package com.example.gamestore.services;


import com.example.gamestore.domain.entities.User;

public interface UserService {

   String registerUser(String[] input);

   String loginUser(String[] input);

   String logOut();

   User getLoggedInUser();


}
