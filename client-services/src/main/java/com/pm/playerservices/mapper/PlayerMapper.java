package com.pm.playerservices.mapper;

import com.pm.playerservices.dto.PlayerRequestdto;
import com.pm.playerservices.dto.PlayerResponsedto;
import com.pm.playerservices.model.Player;

import java.time.LocalDate;

//converts entity into dto format
public class PlayerMapper {
    public static PlayerResponsedto toDTO(Player player) {
        PlayerResponsedto playerDTO = new PlayerResponsedto();
        playerDTO.setId(player.getId().toString());
        playerDTO.setName(player.getName().toString());
        playerDTO.setEmail(player.getEmail().toString());
        return playerDTO;
    }

    public static Player toModel(PlayerRequestdto playerRequestdto) {
        Player player = new Player();
        player.setName(playerRequestdto.getName());
        player.setEmail(playerRequestdto.getEmail());
        player.setRegisteredDate(LocalDate.parse(playerRequestdto.getRegisteredDate()));
        player.setPassword(playerRequestdto.getPassword());
        return player;
    }
}
