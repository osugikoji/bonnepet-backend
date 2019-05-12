package br.com.bonnepet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/*DTO contendo as credenciais do usuario*/
@Getter
@Setter
@NoArgsConstructor
public class CredencialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    private String password;
}
