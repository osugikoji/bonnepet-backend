package br.com.bonnepet.dto;

import br.com.bonnepet.domain.Booking;
import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.helper.DateHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookingDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stayInitialDate;

    private String stayFinalDate;

    private List<PetDTO> petDTO;

    private String totalPrice;

    private String status;

    public BookingDetailsDTO(Booking booking) {
        stayInitialDate = DateHelper.parseToDate(booking.getStayInitialDate());
        stayFinalDate = DateHelper.parseToDate(booking.getStayFinalDate());
        petDTO = getPetListDTO(booking.getPetList());
        totalPrice = booking.getTotalPrice();
        status = booking.getStatus();
    }

    private List<PetDTO> getPetListDTO(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();

        for (Pet pet : petList) {
            petDTOList.add(new PetDTO(pet));
        }
        return petDTOList;
    }
}