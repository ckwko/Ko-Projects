package clueGame;

import java.util.ArrayList;
import java.util.Map;

public class Card {
	private String cardName;
	private CardType cardType;
	private String color;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
		setColor("");
	}
	
	public boolean equals(Card target) {
		if(target.getCardName().equals(cardName)) {
			if(target.getCardType().equals(cardType)) {
				return true;
			}
		} else {
			return false;
		}
		
		return false;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCardName() {
		return cardName;
	}
	
	public String toString() {
		return cardName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Player getPlayerObject(ArrayList<Player> players) {
		for (Player p : players) {
			if (p.getName() == cardName) {
				return p;
			}
		}
		return null;
	}
	
	public Room getRoomObject(Map<Character, Room> roomMap) {
		for (Map.Entry<Character, Room> mapElement : roomMap.entrySet()) {
			Room r = mapElement.getValue();
			if (r.getName() == cardName) {
				return r;
			}
		}
		return null;
	}
	
}
