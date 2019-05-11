package br.com.bonnepet.domain.enums;

public enum PetSizeEnum {

	SMALL(0),
	MEDIUM(1),
	LARGE(2);

	private int cod;

	private PetSizeEnum(int cod) {
		this.cod = cod;
	}
	
	public int getCod() {
		return this.cod;
	}
}
