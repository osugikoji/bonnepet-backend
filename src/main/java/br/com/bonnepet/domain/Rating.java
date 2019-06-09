package br.com.bonnepet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal rate;

    private String feedback;

    @JoinColumn(name = "host_id")
    @ManyToOne
    private Host host;

    @JoinColumn(name = "booking_id")
    @OneToOne
    private Booking booking;

    public Rating(BigDecimal rate, String feedback) {
        this.rate = rate;
        this.feedback = feedback;
    }
}
