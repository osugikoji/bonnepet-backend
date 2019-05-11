package br.com.bonnepet.domain.enums;

public enum BehaviourEnum {

	CONFIDENT(0),
	SHY(1),
	AGGRESSIVE(2),
	SOCIABLE(3),
	INDEPENDENT(4);

	private int cod;

	private BehaviourEnum(int cod) {
		this.cod = cod;
	}
	
	public int getCod() {
		return this.cod;
	}
}
