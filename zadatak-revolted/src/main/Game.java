package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import main.cards.Card;
import main.cards.Unit;
import main.serialization.ItemDeserializer;

public class Game {

	public Game() throws JsonParseException, JsonMappingException, IOException {
		List<Card> cards = loadCards();

		Scanner myObj = new Scanner(System.in);
		this.displayHelp();
		System.out.println("To see these tips again type \"help\"\n");
		System.out.println("Enter source entity ID or \"help\" for help");
		String sourceId = myObj.nextLine();

		do {
			if (sourceId.toLowerCase().equals("exit")) {
				break;
			} else if (sourceId.toLowerCase().equals("end round")) {
				for (Card card : cards) {
					if (card.isUnit()) {
						((Unit) card).readyUp();
					}
				}

				System.out.println("The turn has ended, all units are ready to attack again");
			} else if (sourceId.toLowerCase().equals("help")) {
				this.displayHelp();
			} else {
				System.out.println("Enter target entity ID");
				String targetId = myObj.nextLine();

				List<Card> playingCards = findPlayingCards(sourceId, targetId, cards);

				if (playingCards.size() == 2) {
					int result = playingCards.get(0).action(playingCards.get(1));

					if(result >= 0) {
						this.saveCards(cards);
					}
				} else {
					System.out.println("Could not find the selected cards, try again");
				}
			}

			System.out.println("Enter source entity ID or \"help\" for help");
			sourceId = myObj.nextLine();
		} while (!sourceId.toLowerCase().equals("exit"));

		myObj.close();
	}

	private void displayHelp() {
		System.out.println("To end the current round type \"end round\"");
		System.out.println("Since there is no win condition type \"exit\" to terminate the program\n");
	}

	private List<Card> findPlayingCards(String sourceId, String targetId, List<Card> cards) {
		List<Card> playingCards = new ArrayList<>();
		int found = 0;

		for (int i = 0; i < cards.size() && found != 2; i++) {
			Unit unit = (Unit) cards.get(i);

			if (unit.getEntityId().equals(sourceId)) {
				playingCards.add(unit);
				found++;
			}
			
			if (unit.getEntityId().equals(targetId)) {
				playingCards.add(unit);
				found++;
			}
		}

		if (found == 2 && !playingCards.get(0).getEntityId().equals(sourceId)) {
			Collections.swap(playingCards, 0, 1);
		}

		return playingCards;
	}

	private void saveCards(List<Card> cards) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File("resources/board.json"), cards);
	}

	private List<Card> loadCards() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Card.class, new ItemDeserializer());
		objectMapper.registerModule(module);
		return objectMapper.readValue(new File("resources/board.json"), new TypeReference<List<Card>>(){});
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Starting game");
		new Game();
	}
}
