package br.com.bonnepet.domain;

import br.com.bonnepet.dto.input.PetDTO;
import br.com.bonnepet.helper.DateHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pictureUrl;

    private String name;

    private String breed;

    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String size;

    private String observations;

    @JoinColumn(name = "user_id")
    @ManyToOne()
    private User user;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "PET_BEHAVIOUR",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "behaviour_id"))
    private List<Behaviour> behaviours = new ArrayList<>();

    public Pet(String pictureUrl, String name, String breed, String gender, Date birthDate, String size, String observations, User user) {
        this.pictureUrl = pictureUrl;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.birthDate = birthDate;
        this.size = size;
        this.observations = observations;
        this.user = user;
    }

    public Pet(PetDTO petDTO, User user) {
        this.name = petDTO.getName();
        this.breed = petDTO.getBreed();
        this.gender = petDTO.getGender();
        this.birthDate = DateHelper.parseToDate(petDTO.getBirthDate());
        this.size = petDTO.getSize();
        this.observations = petDTO.getObservations();
        this.user = user;
    }
}
