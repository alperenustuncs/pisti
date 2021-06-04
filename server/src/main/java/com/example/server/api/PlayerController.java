package com.example.server.api;

import com.example.server.model.Player;
import com.example.server.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * This class is the Rest api for player model
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 *
 */
@RestController
@RequestMapping("api/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     *
     * @param player player to be added
     * @return player's information
     */
    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player)  {
        return ResponseEntity.ok().body(this.playerService.addPlayer(player));
    }

    //I don't know what happens if the password is incorrect.
    @PostMapping("/login")
    public ResponseEntity<Player> login(@RequestBody Map<String, ?> input){
        String username = (String)input.get("username");
        String password = ((String)input.get("password"));
        System.out.println("username "+username+"password"+password);
        Player player = this.playerService.findPlayer(username,password);
        return ResponseEntity.ok().body(player);
    }
    /**
     *
     * @param player player to be updated
     * @return updated player information
     */
    @PostMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(this.playerService.updatePlayer(player));
    }

    /**
     *
     * @param username of the player to be found
     * @return asked player's information
     */
    @GetMapping("/find")
    public ResponseEntity<Player> findPlayer(@RequestParam String username) {
        return ResponseEntity.ok().body(this.playerService.findPlayer(username));
    }

    /**
     *
     * @return all the players
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(this.playerService.getAllPlayers());
    }

    /**
     *
     * @param  id of the player to be deleted
     * @return an empty response that shows all the players are deleted
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePlayer(@RequestParam int id) {
        this.playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * @return an empty response that shows all the players are deleted
     * for test purposes
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllPlayers() {
        List<Player> players = this.playerService.getAllPlayers();
        List<Integer> ids = new ArrayList<Integer>();
        for (Player player : players)
            ids.add(player.getId());
        this.playerService.deletePlayer(ids);
        return ResponseEntity.noContent().build();
    }
}

