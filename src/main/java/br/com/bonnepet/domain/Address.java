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
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String district;
    private String street;
    private String number;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Address(String cep, String district, String street, String number, City city) {
        this.cep = cep;
        this.district = district;
        this.street = street;
        this.number = number;
        this.city = city;
    }
}
