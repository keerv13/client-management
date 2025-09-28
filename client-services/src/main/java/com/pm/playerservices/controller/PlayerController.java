package com.pm.playerservices.controller;

import com.pm.playerservices.dto.PlayerRequestdto;
import com.pm.playerservices.dto.PlayerResponsedto;
import com.pm.playerservices.dto.validators.CreatePlayerValidationGroup;
import com.pm.playerservices.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@Tag(name = "Player", description = "API for managing Players")
//handles all requests that starts with /players "https://localhost:4000/players
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //tells spring this is to handle get req
    @GetMapping
    @Operation(summary = "Get Players")
    public ResponseEntity<List<PlayerResponsedto>> getPlayers() {
        List<PlayerResponsedto> players = playerService.getPlayers();
        return ResponseEntity.ok().body(players);
    }

    @PostMapping
    @Operation(summary = "Create a new player")
    //valid tag in Spring checks if the validation of properties in dto have any errors
    // request body convert json request into player request dto
    public ResponseEntity<PlayerResponsedto> createPlayer(@Validated({Default.class, CreatePlayerValidationGroup.class}) @RequestBody PlayerRequestdto playerRequestdto) {
        PlayerResponsedto playerResponsedto = playerService.createPlayer(playerRequestdto);
        return ResponseEntity.ok().body(playerResponsedto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a player")
    public ResponseEntity<PlayerResponsedto> updatePlayer(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PlayerRequestdto playerRequestdto){
        PlayerResponsedto playerResponsedto = playerService.updatePlayer(id, playerRequestdto);

        return ResponseEntity.ok().body(playerResponsedto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a player")//gets uuid from url, wont return anything so void
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();

    }
}
