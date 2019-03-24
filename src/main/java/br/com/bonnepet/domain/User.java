package br.com.bonnepet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private String telephone;

    private String cellphone;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public User(String email, String password, String name, String telephone, String cellphone, Address address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.address = address;
    }
}
