package br.com.bonnepet.dto.input;

import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/*DTO que recebe os dados de cadastro do usuario*/
@Getter
@Setter
@NoArgsConstructor
public class NewUserDTO {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String email;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String password;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String name;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String birthDate;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String cellphone;

    private String telephone;

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
}
