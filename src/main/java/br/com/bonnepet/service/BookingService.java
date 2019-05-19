package br.com.bonnepet.service;

import br.com.bonnepet.domain.Booking;
import br.com.bonnepet.domain.Host;
import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.domain.enums.BookingStatusEnum;
import br.com.bonnepet.dto.NewBookingDTO;
import br.com.bonnepet.repository.BookingRepository;
import br.com.bonnepet.repository.HostRepository;
import br.com.bonnepet.repository.PetRepository;
import br.com.bonnepet.repository.UserRepository;
import br.com.bonnepet.security.UserSS;
import br.com.bonnepet.service.exception.ExceptionMessages;
import br.com.bonnepet.service.exception.ValidationException;
import com.amazonaws.services.licensemanager.model.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private HostRepository hostRepository;

    @Transactional
    public void insertBooking(NewBookingDTO newBookingDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }
        User user = userRepository.findById(userSS.getId()).orElse(new User());

        Host host = hostRepository.findById(new Integer(newBookingDTO.getHostId())).orElse(new Host());

        if (host.getUser() == user) {
            throw new ValidationException(ExceptionMessages.CANNOT_BOOK_YOURSELF);
        }

        List<Booking> userAndHostBookings = bookingRepository.findBookingsByHostAndUser(host, user);
        for (Booking booking : userAndHostBookings) {
            if (!BookingStatusEnum.FINALIZED.name().equals(booking.getStatus())) {
                throw new ValidationException(ExceptionMessages.BOOK_ALREADY_REQUESTED);
            }
        }

        List<Integer> petIds = new ArrayList<>();
        for (String petId : newBookingDTO.getPetIds()) {
            petIds.add(Integer.parseInt(petId));
        }

        List<Pet> petList = petRepository.findAllByIdIn(petIds);

        Booking booking = new Booking(newBookingDTO);
        booking.setPetList(petList);
        booking.setHost(host);
        booking.setUser(user);
        bookingRepository.save(booking);

        user.getBookings().add(booking);
        userRepository.save(user);

        host.getBookings().add(booking);
        hostRepository.save(host);
    }
}
