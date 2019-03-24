package br.com.bonnepet.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*DTO contendo as credenciais do usuario*/
@Getter
@Setter
@NoArgsConstructor
public class CredencialsDTO {
    private static final long serialVersionUID = 1L;

    private String email;

    private String password;
}
