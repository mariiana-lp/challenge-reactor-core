package com.example.demo.Services;

import com.example.demo.model.Player;
import org.apache.commons.lang3.builder.CompareToBuilder;
import reactor.core.publisher.Flux;

import static java.lang.Integer.compare;

public class PlayerService {

    public Flux<Player> findPlayerByOlderThan34Years(Flux<Player> playerFlux){
        return playerFlux.filter(player -> player.age > 34);
    }

    public Flux<Player> findPlayerByClub(Flux<Player> playerFlux, String club){
        return playerFlux.filter(player -> player.getClub().equalsIgnoreCase(club));
    }

    public Flux<String> generateNationalList(Flux<Player> playerFlux){
        return playerFlux.map(player -> player.getNational())
                .sort()
                .distinct();
    }

    public Flux<Player> top5BestPlayers (Flux<Player> playerFlux){
        return playerFlux.sort((p1,p2 ) -> compare(p2.getWinners(),p1.getWinners()))
                .take(5);
    }

    public Flux<Player> rankingPlayersByNational (Flux<Player> playerFlux, String national){
        return playerFlux
                .filter(player -> player.getNational().equalsIgnoreCase(national))
                .sort((p1,p2 ) -> compare(p2.getWinners(),p1.getWinners()))
                .take(5);
    }
}
