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
public class NewBookingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String hostId;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String stayInitialDate;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String stayFinalDate;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private List<String> petIds;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String totalPrice;
}
