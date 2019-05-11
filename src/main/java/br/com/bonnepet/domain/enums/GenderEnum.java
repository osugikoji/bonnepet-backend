package br.com.bonnepet.domain.enums;

public enum GenderEnum {

	MALE(0),
	FEMALE(1);

	private int cod;

	private GenderEnum(int cod) {
		this.cod = cod;
	}
	
	public int getCod() {
		return this.cod;
	}
}
