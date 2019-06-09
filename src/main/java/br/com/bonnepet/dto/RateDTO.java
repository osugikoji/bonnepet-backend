package br.com.bonnepet.dto;

import br.com.bonnepet.domain.Rating;
import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String bookingId;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String rate;

    private String feedback;

    public RateDTO(Rating rating) {
        this.bookingId = rating.getId().toString();
        this.rate = rating.getRate().toString();
        this.feedback = rating.getFeedback();
    }
}
