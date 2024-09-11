package clueGame;

public class Solution {
	private Card room;
	private Card player;
	private Card weapon;
	
	public Solution(Card player, Card weapon, Card room) {
		this.player = player;
		this.weapon = weapon;
		this.room = room;
	}

	public Card getRoom() {
		return room;
	}

	public Card getPlayer() {
		return player;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	public String getRoomName() {
		return room.getCardName();
	}
	
	public String getPlayerName() {
		return player.getCardName();
	}
	
	public String getWeaponName() {
		return weapon.getCardName();
	}
}
