package com.example.server.service;


import com.example.server.model.Player;
import com.example.server.model.SecureToken;
import com.example.server.repository.PlayerRepository;
import com.example.server.repository.SecureTokenRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the implementation of the PlayerService interface. This class is a wrapper class
 * for crud operations of Player entity.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@Service
public class PlayerServiceImpl implements PlayerService{

    /**
     *
     * @return PasswordEncoder object
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;

    private String tokenMessage = "Your token is: ";

    //forgotten password part

    public void forgottenPassword(String email){
        Player player = playerRepository.findPlayerByEmail(email);
        sendResetPasswordEmail(player);
    }

    /**
     * updates password if token is valid
     * @param password
     * @param token
     */
    public void updatePassword(String password, String token){
        SecureToken secureToken = secureTokenService.findByToken(token);

        if(secureToken == null || secureToken.isExpired()){//normally exception needs to be thrown
            return;
        }

        Player player = secureToken.getPlayer();
        player.setPassword(password);
        updatePlayer(player);
    }

    protected void sendResetPasswordEmail(Player player){
        //sending an email
        System.out.println("sendResetPasswordEmail start!");
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setPlayer(player);
        secureTokenRepository.save(secureToken);
        System.out.println("im at sendResetPasswordEmail"+player.getEmail());
        System.out.println(tokenMessage);
        try {
            emailService.sendSimpleEmail(player.getEmail(),"Password Reset", tokenMessage + secureToken.getToken());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //kendisi zorla override ettirdi
    @Override
    public String encodeTest(String pass) {
        return null;
    }

    /**
     *
     * @param player Player to be added
     * @return if the given informations has unique email and username it returns newly added
     * Player otherwise it returns null.
     */
    @Override
    public Player addPlayer(Player player) {
        if (playerRepository.findPlayerByEmail(player.getEmail()) != null
                ||
                playerRepository.findPlayerByUsername(player.getUsername()) != null)
            return null;
        Player playerToAdd = new Player();
        playerToAdd.setUsername(player.getUsername());
        playerToAdd.setEmail(player.getEmail());
        playerToAdd.setPassword(player.getPassword());
        //playerToAdd.setPassword(passwordEncoder.encode(player.getPassword()));encoding will be on frontend
        return playerRepository.save(playerToAdd);
    }

    /**
     *
     * @return all the players
     */
    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     *
     * @param player player to be updated
     * @return updated player
     */
    @Override
    public Player updatePlayer(Player player) {
        Player playerToUpdate = playerRepository.findPlayerByUsername(player.getUsername());
        playerToUpdate.setUsername(player.getUsername());
        playerToUpdate.setEmail(player.getEmail());
        playerToUpdate.setPassword((player.getPassword()));
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
    }

    /**
    * Deletes the player with given id
    * @param id of the player to be deleted
    * */
    @Override
    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    /**
     * Deletes players with given ids
     * @param idList player ids to be deleted.
     */
    @Override
    public void deletePlayer(List<Integer> idList) {
        for (int id : idList) {
            playerRepository.deleteById(id);
        }
    }

    /**
     * Returns a player using given username
     * @param username username of the player
     * @return desired player
     */
    @Override
    public Player findPlayer(String username) {
        return playerRepository.findPlayerByUsername(username);
    }




    /**
     * Return a player using player's id
     * @param id player's id
     * @return queried player
     */
    @Override
    public Player findPlayer(int id) {
        return playerRepository.getOne(id);
    }

    public Player findPlayer(String username, String password){
        return playerRepository.findPlayerByUsernameAndPassword(username, password);
    }
}
