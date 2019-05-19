package br.com.bonnepet.dto;

import br.com.bonnepet.domain.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private ProfileDTO profileDTO;

    private List<PetDTO> petDTO;

    private String price;

    private List<String> sizePreferenceList;

    private String about;

    private BookingDetailsDTO bookingDetailsDTO;

    public HostDTO(ProfileDTO profileDTO, List<PetDTO> petDTO, String price, List<Size> sizePreferenceList, String about) {
        this.profileDTO = profileDTO;
        this.petDTO = petDTO;
        this.price = price;
        this.sizePreferenceList = getBehaviours(sizePreferenceList);
        this.about = about;
    }

    private List<String> getBehaviours(List<Size> behaviours) {
        List<String> behaviourList = new ArrayList<>();
        for (Size behaviour : behaviours) behaviourList.add(behaviour.getName());
        return behaviourList;
    }
}
