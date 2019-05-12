package br.com.bonnepet.dto;

import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewHostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String price;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private List<String> sizeList;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String about;
}
