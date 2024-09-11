package clueGame;

public enum CardType {
	ROOM("Room"), PERSON("Person"), WEAPON("Weapon");

	private String value;
	
	CardType(String string) {
		// TODO Auto-generated constructor stub
		this.value = string;
	}
	
	@Override
    public String toString() {
        return value;
    }
	
	
}
