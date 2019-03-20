package br.com.lardopet.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private Integer telephone;

    private Integer cellphone;

    public User(String email, String password, String name, Integer telephone, Integer cellphone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.cellphone = cellphone;
    }
}
