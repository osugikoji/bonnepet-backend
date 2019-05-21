package br.com.bonnepet.resource;

import br.com.bonnepet.dto.HostDTO;
import br.com.bonnepet.dto.NewBookingDTO;
import br.com.bonnepet.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/requested")
    public ResponseEntity<List<HostDTO>> getRequestedBookings() {
        List<HostDTO> bookingDTOList = bookingService.getRequestedBookings();
        return ResponseEntity.ok(bookingDTOList);
    }

}
