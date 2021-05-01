package com.example.server;

import com.example.server.api.PlayerController;
import com.example.server.model.Player;
import com.example.server.repository.PlayerRepository;
import com.example.server.service.PlayerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**Starting point of the server, it includes main function.
 * @author Nureddin Alperen Ustun & Mustafa Ali Akcay
 */
@SpringBootApplication
public class ServerApplication {

    /** 
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
