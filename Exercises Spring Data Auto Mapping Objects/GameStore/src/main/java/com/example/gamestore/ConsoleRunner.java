package com.example.gamestore;

import com.example.gamestore.domain.dtos.GameDTO;
import com.example.gamestore.domain.repositories.GameRepository;
import com.example.gamestore.services.GameService;
import com.example.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;

    private final GameService gameService;


    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        while (!input.equals("close")){

            String[] arguments = input.split("\\|");
            String command = arguments[0];


            String output = switch (command) {
                case "RegisterUser" -> userService.registerUser(arguments);

                case "LoginUser" -> userService.loginUser(arguments);

                case "Logout" -> userService.logOut();

                case "AddGame" -> gameService.addGame(arguments);

                case "EditGame" -> gameService.editGame(arguments);

                case "DeleteGame" -> gameService.removeGame(arguments);

                default -> "no command found";
            };
            System.out.println(output);

            input = scanner.nextLine();
        }

        String command = scanner.nextLine();

        while (!command.equals("end")){
            switch (command){
                //case "AllGames" -> ();
            }


            command = scanner.nextLine();
        }




    }

}
