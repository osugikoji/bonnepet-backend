package br.com.bonnepet.domain;

import br.com.bonnepet.domain.enums.BookingStatusEnum;
import br.com.bonnepet.dto.NewBookingDTO;
import br.com.bonnepet.helper.DateHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date stayInitialDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date stayFinalDate;

    private String totalPrice;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @JoinColumn(name = "host_id")
    @ManyToOne
    private Host host;

    @OneToMany
    private List<Pet> petList = new ArrayList<>();

    public Booking(String status, Date stayInitialDate, Date stayFinalDate, String totalPrice, User user, List<Pet> petList) {
        this.status = status;
        this.stayInitialDate = stayInitialDate;
        this.stayFinalDate = stayFinalDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.petList = petList;
    }

    public Booking(NewBookingDTO newBookingDTO) {
        this.status = BookingStatusEnum.OPEN.name();
        this.stayInitialDate = DateHelper.parseToDate(newBookingDTO.getStayInitialDate());
        this.stayFinalDate = DateHelper.parseToDate(newBookingDTO.getStayFinalDate());
        this.totalPrice = newBookingDTO.getTotalPrice();
    }
}
