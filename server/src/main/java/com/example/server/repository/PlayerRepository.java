package com.example.server.repository;

import com.example.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class is repository for Player entity.
 * Since username and email are unique fields, functions regarding finding a player using
 * these fields are added to repository.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 *
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findPlayerByUsername(String username);
    Player findPlayerByEmail(String email);
    Player findPlayerByEmailAndPassword(String username, String password);//For login

    Player findPlayerByUsernameAndPassword(String username, String password);
}
