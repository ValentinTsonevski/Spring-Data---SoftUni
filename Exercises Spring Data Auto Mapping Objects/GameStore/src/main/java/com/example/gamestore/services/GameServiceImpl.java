package com.example.gamestore.services;

import com.example.gamestore.domain.dtos.GameDTO;
import com.example.gamestore.domain.entities.Game;
import com.example.gamestore.domain.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser() != null && this.userService.getLoggedInUser().isAdministrator()) {

            String title = args[1];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(args[2]));
            double size = Double.parseDouble(args[3]);
            String trailer = args[4];
            String url = args[5];
            String description = args[6];
            LocalDate releaseDate = LocalDate.now();

            GameDTO gameDTO = new GameDTO(title, price, size, trailer, url, description, releaseDate);

            Game gameToAdd = this.modelMapper.map(gameDTO, Game.class);
            boolean checkIfGameExist = this.gameRepository.findFirstByTitle(title).isPresent();


            if (checkIfGameExist) {
                return ("The game is already in the catalog");
            }
            gameRepository.save(gameToAdd);

            return String.format("Added %s", gameToAdd.getTitle());
        }
            return "Low level aces";

    }


    @Override
    public String editGame(String[] args) {

        Long id = Long.parseLong(args[1]);

        if(!gameRepository.existsById(id) || !userService.getLoggedInUser().isAdministrator()){
            return "You dont have aces to do edits.";
        }

        Game gameById = this.gameRepository.findById(id).get();
        Game game = this.modelMapper.map(gameById, Game.class);
        edit(args, game);
        this.gameRepository.save(game);

        return String.format("Edited %s",game.getTitle());
    }

    @Override
    public String removeGame(String[] args) {
        Long id = Long.parseLong(args[1]);

        if(!gameRepository.existsById(id)){
            return "Game not exist";
        }

        Game game = gameRepository.findById(id).get();
        String gameTitle = game.getTitle();

        this.gameRepository.deleteById(id);
        return String.format("Deleted %s",gameTitle);
    }


    private static void edit(String[] args, Game game) {
        for (int i = 2; i < args.length; i++) {
            String field = args[i].split("=")[0];
            String value = args[i].split("=")[1];

            switch (field){
                case "title" -> game.setTitle(value);
                case "trailer" -> game.setTrailer(value);
                case "url" -> game.setUrl(value);
                case "description" -> game.setDescription(value);
                case "price" -> game.setPrice(BigDecimal.valueOf(Double.parseDouble(value)));
                case "size" -> game.setSize(Double.parseDouble(value));
                case "releaseDate" -> game.setReleaseDate(LocalDate.parse(value));
            }

        }
    }


}
