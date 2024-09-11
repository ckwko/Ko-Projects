package clueGame;

import java.util.*;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, String row, String col) {
		super(name, color, row, col, false);
	}
	
	/*
	 * create a suggestion with randomized choices from cards that have not been seen yet
	 */
	public Solution createSuggestion(ArrayList<Card> deck, String roomName) {
		Set<Card> unseenCards = findUnseenCards(deck);
		ArrayList<Card> unseenPlayers = new ArrayList<Card>();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		System.out.println(roomName);

	
		for (Card c : unseenCards) {
			CardType type = c.getCardType();
			
			switch(type) {
				case PERSON:
					unseenPlayers.add(c);
					break;
					
				case WEAPON:
					unseenWeapons.add(c);
					break;
					
				case ROOM:
					break;
			}
		}
		
		System.out.println(roomName);
		Random rand = new Random();
		int randomIndex =  rand.nextInt(unseenPlayers.size());
		Card player = unseenPlayers.get(randomIndex);
		
		randomIndex = rand.nextInt(unseenWeapons.size());
		Card weapon = unseenWeapons.get(randomIndex);
		
		Card room = new Card(roomName, CardType.ROOM);

		Solution sol = new Solution(player, weapon, room);
		return sol;
	}
	
	/*
	 * randomly select a target to move to
	 * unseen rooms take priority
	 */
	public BoardCell selectTarget(Set<BoardCell> targets, ArrayList<Card> deck, Map<Character, Room> roomMap) {
		BoardCell target = new BoardCell(0,0);
		Set<Card> unseenCards = findUnseenCards(deck);
		
		// check unseen cards to see if rooms are available to target
		for(BoardCell t : targets) {
			String roomName = roomMap.get(t.getInitial()).getName();
			for(Card c : unseenCards) {
				if (roomName.equals(c.getCardName())) {
					return t;
				} 
			}
		}
		
		// select random target from targets
		Random random = new Random();
		int randomIndex = random.nextInt(targets.size());
		int targetIndex = 0;
		
		for(BoardCell t : targets) {
			if(targetIndex == randomIndex) {
				target = t;
			} 
			
			targetIndex++;
		}
		
		return target;
	}
	
}
