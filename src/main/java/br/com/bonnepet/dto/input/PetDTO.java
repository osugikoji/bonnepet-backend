package br.com.bonnepet.dto.input;

import br.com.bonnepet.domain.Behaviour;
import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.helper.DateHelper;
import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PetDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String pictureURL;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String name;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String breed;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String gender;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private List<String> behaviours;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String birthDate;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String size;

    private String observations;

    public PetDTO(Pet pet) {
        this.id = pet.getId().toString();
        this.pictureURL = pet.getPictureUrl();
        this.name = pet.getName();
        this.breed = pet.getBreed();
        this.gender = pet.getGender();
        this.behaviours = getBehaviours(pet.getBehaviours());
        this.birthDate = DateHelper.parseToDate(pet.getBirthDate());
        this.size = pet.getSize();
        this.observations = pet.getObservations();
    }

    private List<String> getBehaviours(List<Behaviour> behaviours) {
        List<String> behaviourList = new ArrayList<>();
        for (Behaviour behaviour : behaviours) behaviourList.add(behaviour.getName());
        return behaviourList;
    }

}
