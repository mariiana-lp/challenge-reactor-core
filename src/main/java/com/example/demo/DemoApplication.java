package com.example.demo;

import com.example.demo.Services.PlayerService;
import com.example.demo.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	static List<Player> players = CsvUtilFile.getPlayers();
	static Flux<Player> playerFlux = Flux.fromStream(players.parallelStream()).cache();
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

		PlayerService playerService = new PlayerService();

		Flux<Player> playerFilterByAge = playerService.findPlayerByOlderThan34Years(playerFlux);
		playerFilterByAge
				.take(5)
				.subscribe(players -> log.info("Nombre: " + players.getName() + "\n edad: " + players.getAge()));

		Flux<Player> playerFilterByclub = playerService.findPlayerByClub(playerFlux, "Barzagli Juventus");
		playerFilterByclub
				.take(5)
				.subscribe(players -> log.info("Nombre: " + players.getName() + "\n Club: " + players.getClub()));

		Flux<String> nationalList = (Flux<String>) playerService.generateNationalList(playerFlux);
		nationalList.collectList()
				.subscribe(list -> log.info(list.toString()));

		Flux<Player> top5BestPlayers = playerService.top5BestPlayers(playerFlux);
		top5BestPlayers.subscribe(player -> log.info(player.toString()));

		Flux<Player> rankingPlayersByNational = playerService.rankingPlayersByNational(playerFlux, "Belgium");
		rankingPlayersByNational.subscribe(player -> log.info(player.toString()));
	}

}
