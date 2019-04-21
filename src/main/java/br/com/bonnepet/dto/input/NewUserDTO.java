package br.com.bonnepet.dto.input;

import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.AddressDTO;
import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/*DTO que recebe os dados de cadastro do usuario*/
@Getter
@Setter
@NoArgsConstructor
public class NewUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

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

    private AddressDTO addressDTO;

    public NewUserDTO(User user) {
        this.id = user.getId().toString();
    }
}
