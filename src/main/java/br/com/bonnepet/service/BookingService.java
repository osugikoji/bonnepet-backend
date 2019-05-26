package br.com.bonnepet.service;

import br.com.bonnepet.domain.Booking;
import br.com.bonnepet.domain.Host;
import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.domain.enums.BookingStatusEnum;
import br.com.bonnepet.dto.*;
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
            if (!BookingStatusEnum.FINALIZED.name().equals(booking.getStatus()) && !BookingStatusEnum.REFUSED.name().equals(booking.getStatus())) {
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

    @Transactional
    public List<HostDTO> getRequestedBookings() {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        User user = userRepository.findById(userSS.getId()).orElse(new User());

        List<Booking> bookList = user.getBookings();

        List<HostDTO> hostDTOList = new ArrayList<>();
        for (Booking booking : bookList) {
            ProfileDTO hostProfileDTO = new ProfileDTO(booking.getHost().getUser());
            List<PetDTO> petHostDTOList = getPetDTOList(booking.getHost().getUser().getPets());
            HostDTO hostDTO = new HostDTO(hostProfileDTO, petHostDTOList, booking.getHost().getPrice().toString(),
                    booking.getHost().getPreferenceSizes(), booking.getHost().getAbout());
            hostDTO.setId(booking.getHost().getId().toString());
            BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO(booking);
            hostDTO.setBookingDetailsDTO(bookingDetailsDTO);
            hostDTOList.add(hostDTO);
        }
        return hostDTOList;
    }

    @Transactional
    public List<HostBookingDTO> getBookingsHost() {

        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Host host = hostRepository.findByUserId(userSS.getId());

        if (host == null) {
            throw new ValidationException(ExceptionMessages.NOT_A_HOST);
        }

        List<HostBookingDTO> hostBookingDTOList = new ArrayList<>();
        for (Booking booking : host.getBookings()) {
            ProfileDTO profileDTO = new ProfileDTO(booking.getUser());
            BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO(booking);
            HostBookingDTO hostBookingDTO = new HostBookingDTO(profileDTO, bookingDetailsDTO);
            hostBookingDTOList.add(hostBookingDTO);
        }
        return hostBookingDTOList;
    }

    @Transactional
    public void cancelBooking(Integer id) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Booking booking = bookingRepository.findById(id).orElse(new Booking());

        if (!BookingStatusEnum.OPEN.name().equals(booking.getStatus())) {
            throw new ValidationException(ExceptionMessages.CANNOT_CANCEL_BOOKING);
        }

        Host host = booking.getHost();
        host.getBookings().remove(booking);
        hostRepository.save(host);

        User user = booking.getUser();
        user.getBookings().remove(booking);
        userRepository.save(user);

        bookingRepository.delete(booking);
    }

    @Transactional
    public HostBookingDTO refuseBooking(Integer id) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Booking booking = bookingRepository.findById(id).orElse(new Booking());

        if (!BookingStatusEnum.OPEN.name().equals(booking.getStatus())) {
            throw new ValidationException(ExceptionMessages.CANNOT_REFUSE_BOOKING);
        }
        booking.setStatus(BookingStatusEnum.REFUSED.name());
        booking = bookingRepository.save(booking);

        ProfileDTO profileDTO = new ProfileDTO(booking.getUser());
        BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO(booking);
        return new HostBookingDTO(profileDTO, bookingDetailsDTO);
    }

    @Transactional
    public HostBookingDTO acceptBooking(Integer id) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Booking booking = bookingRepository.findById(id).orElse(new Booking());

        if (!BookingStatusEnum.OPEN.name().equals(booking.getStatus())) {
            throw new ValidationException(ExceptionMessages.CANNOT_REFUSE_BOOKING);
        }
        booking.setStatus(BookingStatusEnum.CONFIRMED.name());
        booking = bookingRepository.save(booking);

        ProfileDTO profileDTO = new ProfileDTO(booking.getUser());
        BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO(booking);
        return new HostBookingDTO(profileDTO, bookingDetailsDTO);
    }

    private List<PetDTO> getPetDTOList(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : petList) {
            PetDTO petDTO = new PetDTO(pet);
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }
}
