package com.pm.playerservices.service;

import com.pm.playerservices.dto.PlayerRequestdto;
import com.pm.playerservices.dto.PlayerResponsedto;
import com.pm.playerservices.exception.EmailAlreadyExistsException;
import com.pm.playerservices.exception.PlayerNotFoundException;
import com.pm.playerservices.grpc.ScoreServiceGrpcClient;
import com.pm.playerservices.mapper.PlayerMapper;
import com.pm.playerservices.model.Player;
import com.pm.playerservices.repository.PlayerRepository;
import com.pm.playerservices.kafka.kafkaProducer;
import org.springframework.stereotype.Service;
import score.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private final ScoreServiceGrpcClient scoreServiceGrpcClient;
    private final kafkaProducer kafkaProducer;

    public PlayerService(PlayerRepository playerRepository, ScoreServiceGrpcClient scoreServiceGrpcClient, kafkaProducer KafkaProducer) {
        this.playerRepository = playerRepository;
        this.scoreServiceGrpcClient = scoreServiceGrpcClient;
        this.kafkaProducer = KafkaProducer;
    }
    //built in jpa method to create a query
    public List<PlayerResponsedto> getPlayers(){
        List<Player> players = playerRepository.findAll();

        List<PlayerResponsedto> playerResponsedtos = players.stream()
                .map(player -> PlayerMapper.toDTO(player)).toList();
        //plsyerstreammap is to itterate over a list like for loop then gives the player its currently on in the loop

        return playerResponsedtos;
    }

    //Handle requests
    public PlayerResponsedto createPlayer(PlayerRequestdto playerRequestdto){
        if (playerRepository.existsByEmail(playerRequestdto.getEmail())) {
            throw new EmailAlreadyExistsException("A player with this email already exists " + playerRequestdto.getEmail());
        }

        //if email exist this code wont run
        Player newPlayer = playerRepository.save(
                PlayerMapper.toModel(playerRequestdto)
        );
        scoreServiceGrpcClient.createScoreAccount(
                newPlayer.getId().toString(),
                "init-paper",       // placeholder
                "init-token",       // placeholder
                Answer.newBuilder()
                        .setQuestionId("init")
                        .setChosenOptionId("none")
                        .build()
        );

        kafkaProducer.sendEvent(newPlayer);

        return PlayerMapper.toDTO(newPlayer);

    }

    public PlayerResponsedto updatePlayer(UUID id, PlayerRequestdto playerRequestdto){
        Player player = playerRepository.findById(id).orElseThrow(()-> new PlayerNotFoundException("Player not found with ID " + id));
        //check for users with same email but diff id
        if (playerRepository.existsByEmailAndIdNot(playerRequestdto.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A player with this email already exists " + playerRequestdto.getEmail());
        }

        player.setName(playerRequestdto.getName());
        player.setEmail(playerRequestdto.getEmail());
        player.setPassword(playerRequestdto.getPassword());

        Player updatedPlayer = playerRepository.save(player);
        return PlayerMapper.toDTO(updatedPlayer);

    }

    public void deletePlayer(UUID id){
        playerRepository.deleteById(id);
    }
}
