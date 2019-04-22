package br.com.bonnepet.domain;

import br.com.bonnepet.dto.output.ProfileDTO;
import br.com.bonnepet.helper.DateHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pictureUrl;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String telephone;

    private String cellphone;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public User(String pictureUrl, String email, String password, String name, Date birthDate, String telephone, String cellphone, Address address) {
        this.pictureUrl = pictureUrl;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.address = address;
    }

    public User(ProfileDTO profileDTO) {
        this.email = profileDTO.getEmail();
        this.name = profileDTO.getUserName();
        this.birthDate = DateHelper.parseToDate(profileDTO.getBirthDate());
        this.telephone = profileDTO.getTelephone();
        this.cellphone = profileDTO.getCellphone();
        this.address = address;
    }
}
