package com.example.gamestore.services;

import com.example.gamestore.domain.dtos.LoginUserDto;
import com.example.gamestore.domain.dtos.UserRegisterDTO;
import com.example.gamestore.domain.entities.User;
import com.example.gamestore.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.gamestore.Constants.INCORRECT_PASSWORD;

@Service
public class UserServiceImpl implements UserService {
    private   User loggedInUser;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] input) {

        String email = input[1];
        String password = input[2];
        String confirmPassword = input[3];
        String fullName = input[4];

        UserRegisterDTO userRegisterDTO;

        try {
            userRegisterDTO = new UserRegisterDTO(email, password, confirmPassword, fullName);

        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }

        User registeredUser = modelMapper.map(userRegisterDTO, User.class);

        if (this.userRepository.count() == 0) {
            registeredUser.setAdministrator(true);
        }

        boolean isUserRegistered = userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent();

        if (isUserRegistered) {
           // throw new IllegalArgumentException("Email already exist.");
            return "Email already exist";
        }

        this.userRepository.save(registeredUser);

        return String.format("%s was registered", registeredUser.getFullName());
    }


    @Override
    public String loginUser(String[] input) {


        String email = input[1];
        String password = input[2];

        LoginUserDto loginUserDto = new LoginUserDto(email, password);

        Optional<User> user = this.userRepository.findFirstByEmail(loginUserDto.getEmail());

        if(user.isPresent() && this.loggedInUser == null && user.get().getPassword().equals(loginUserDto.getPassword())){

          this.loggedInUser = user.get();

          return String.format("Successfully logged in %s",loggedInUser.getFullName());
        }

        return INCORRECT_PASSWORD;
    }

    @Override
    public String logOut() {

        if(loggedInUser == null){
            return "Cannot log out. No user was logged in.";
        }

        String lastUserName = loggedInUser.getFullName();
         loggedInUser = null;

        return String.format("User %s successfully logged out",lastUserName);
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }


}
