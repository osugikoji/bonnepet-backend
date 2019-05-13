package br.com.bonnepet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Host implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "HOST_SIZE",
            joinColumns = @JoinColumn(name = "host_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> preferenceSizes = new ArrayList<>();

    private String about;

    @OneToMany
    private List<Booking> bookings = new ArrayList<>();

    public Host(User user, BigDecimal price, String about) {
        this.user = user;
        this.price = price;
        this.about = about;
    }
}
