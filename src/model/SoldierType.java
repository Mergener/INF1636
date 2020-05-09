package model;

enum SoldierType {
	SMALL(1),
	LARGE(10);
	
	private final int value;
	
	public int getValue() {
		return (int)value;
	}
	
	SoldierType(int value) {
		this.value = value;
	}
}
