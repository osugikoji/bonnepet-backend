package br.com.bonnepet.domain.enums;

public enum BookingStatusEnum {

    OPEN(0),
    CONFIRMED(1),
    REFUSED(2);

    private int cod;

    private BookingStatusEnum(int cod) {
        this.cod = cod;
    }

    public int getCod() {
        return this.cod;
    }
}
