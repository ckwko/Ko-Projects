package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	private boolean isHuman, disproved;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private Set<Card> seenCards = new HashSet<Card>(hand);
	private String room = null;
	
	public Player(String name, String color, String row, String col, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.row = Integer.parseInt(row);
		this.col = Integer.parseInt(col);
		this.isHuman = isHuman;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean isHuman() {
		return isHuman;
	}
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		if (seenCard != null) {
			seenCards.add(seenCard);
		}
	}

	/*
	 * find if hand has matching cards, if true, return which card matches suggestion
	 */
	public Card disproveSuggestion(Solution suggestion) {
		Card disproveCard = null;
		int numMatching = 0;

		for(Card c : hand) {
			String cardName = c.getCardName();
			if (suggestion.getPlayer().getCardName() == cardName)
				numMatching++;

			if (suggestion.getRoom().getCardName() == cardName)
				numMatching++;

			if (suggestion.getWeapon().getCardName() == cardName)
				numMatching++;
		}
		
		// if multiple cards can disprove, randomly choose one to return
		if (numMatching > 1) {
			int index = (int)(Math.random() * returnMatchingCard(suggestion).size());
			disproveCard = returnMatchingCard(suggestion).get(index);

		} else if(numMatching == 1) {
			disproveCard = returnMatchingCard(suggestion).get(0);

		} else {
			disproveCard = null;

		}
		
		return disproveCard;
	}

	// find which card matches the player hand
	private ArrayList<Card> returnMatchingCard(Solution suggestion) {
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		
		for(Card c : hand) {
			String cardName = c.getCardName();
			if (suggestion.getPlayer().getCardName() == cardName) {
				matchingCards.add(c);

			} else if (suggestion.getRoom().getCardName() == cardName) {
				matchingCards.add(c);

			} else if (suggestion.getWeapon().getCardName() == cardName) {
				matchingCards.add(c);
			}
		}

		return matchingCards;
	}
	
	/*
	 * take the deck and seen cards and find which cards have not been seen yet
	 */
	public Set<Card> findUnseenCards(ArrayList<Card> deck) {
		Set<Card> unseenCards = new HashSet<Card>();
		
		for (Card c : deck) {
			boolean matching = false;
			for (Card seen : seenCards) {
				if(c.equals(seen)) {
					matching = true;
				}
			}
			
			if(!matching) {
				unseenCards.add(c);
			}
		}
		
		return unseenCards;
	}
	
	public int rollDice() {
		// create instance of Random class
        Random rand = new Random();
   
        // Generate random integers in range 0 to 5
        int randInt = rand.nextInt(5) + 1;
        randInt++;
        return randInt;
        
	}

	public String getName() {
		return name;
	}
	
	public Set<Card> getSeenCards() {
		return seenCards;
	}

	public String getColor() {
		return color;
	}
	
	public void drawPlayer(Graphics g, int cellWidth, int cellHeight, int offset) {
		int xPos = (cellWidth * col) + offset;
		int yPos = cellHeight * row;
		Color playerColor = Utility.colorStringToObject(color);
		g.setColor(playerColor);
		g.fillOval(xPos, yPos, cellWidth, cellHeight);
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	public void setDisproved(boolean disproved) {
		this.disproved = disproved;
	}
	
	public boolean getDisproved() {
		return disproved;
	}
	
	/*
	 * check if target card is in hand
	 */
	public boolean checkIsInHand(Card target) {
		for(Card c : hand) {
			if(c.equals(target)) {
				return true;
			}
		}
		return false;
	}
}
