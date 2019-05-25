package br.com.bonnepet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class HostBookingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private ProfileDTO profileDTO;

    private BookingDetailsDTO bookingDetailsDTO;

    public HostBookingDTO(ProfileDTO profileDTO, BookingDetailsDTO bookingDetailsDTO) {
        this.profileDTO = profileDTO;
        this.bookingDetailsDTO = bookingDetailsDTO;
    }
}
