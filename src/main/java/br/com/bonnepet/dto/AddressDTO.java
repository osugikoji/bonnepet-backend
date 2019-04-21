package br.com.bonnepet.dto;

import br.com.bonnepet.domain.Address;
import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String cep;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String district;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String street;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String number;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String state;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String city;

    public AddressDTO(Address address) {
        cep = address.getCep();
        district = address.getDistrict();
        street = address.getStreet();
        number = address.getNumber();
        state = address.getCity().getState().getName();
        city = address.getCity().getName();
    }
}
