package br.com.bonnepet.resource;

import br.com.bonnepet.dto.NewBookingDTO;
import br.com.bonnepet.dto.NewHostDTO;
import br.com.bonnepet.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/bookings")
public class BookResource {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/insert")
    public ResponseEntity<Void> insertBooking(@Valid @RequestBody NewBookingDTO newBookingDTO) {
        bookingService.insertBooking(newBookingDTO);
        return ResponseEntity.noContent().build();
    }

}
