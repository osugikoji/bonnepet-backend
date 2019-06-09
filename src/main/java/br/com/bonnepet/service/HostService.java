package br.com.bonnepet.service;

import br.com.bonnepet.domain.*;
import br.com.bonnepet.domain.enums.BookingStatusEnum;
import br.com.bonnepet.dto.*;
import br.com.bonnepet.repository.BookingRepository;
import br.com.bonnepet.repository.HostRepository;
import br.com.bonnepet.repository.SizeRepository;
import br.com.bonnepet.repository.UserRepository;
import br.com.bonnepet.security.UserSS;
import br.com.bonnepet.service.exception.ExceptionMessages;
import br.com.bonnepet.service.exception.ValidationException;
import com.amazonaws.services.licensemanager.model.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class HostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void insertHost(NewHostDTO newHostDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        if (new BigDecimal(newHostDTO.getPrice()).intValue() <= 0) {
            throw new ValidationException(ExceptionMessages.INVALID_PRICE);
        }

        User user = userRepository.findById(userSS.getId()).orElse(new User());

        Host host = user.getHost();

        if (host != null) {
            throw new ValidationException(ExceptionMessages.ALREADY_HOST);
        }

        host = new Host(user, new BigDecimal(newHostDTO.getPrice()), newHostDTO.getAbout());

        List<Size> sizeList = sizeRepository.findAllByNameIn(newHostDTO.getSizeList());
        host.setPreferenceSizes(sizeList);

        user.setHost(host);
        userRepository.save(user);
    }

    public HostDTO getHost(Integer id) {
        Host host = hostRepository.findById(id).orElse(new Host());

        HostDTO hostDTO;
        ProfileDTO profileDTO = new ProfileDTO(host.getUser());
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : host.getUser().getPets()) {
            petDTOList.add(new PetDTO(pet));
        }
        hostDTO = new HostDTO(profileDTO, petDTOList, host.getPrice().toBigInteger().toString(), host.getPreferenceSizes(), host.getAbout());
        hostDTO.setBookingDetailsDTO(getBookDetailsDTO(host));
        hostDTO.setId(host.getId().toString());

        return hostDTO;
    }

    public List<HostDTO> getAllHosts() {
        List<Host> hostList = hostRepository.findAll();

        List<HostDTO> hostReturnList = new ArrayList<>();
        for (Host host : hostList) {
            HostDTO hostDTO;
            ProfileDTO profileDTO = new ProfileDTO(host.getUser());

            List<PetDTO> petDTOList = new ArrayList<>();
            for (Pet pet : host.getUser().getPets()) {
                petDTOList.add(new PetDTO(pet));
            }

            List<RateDetailsDTO> rateDTOList = new ArrayList<>();
            for (Rating rating : host.getRatings()) {
                rateDTOList.add(new RateDetailsDTO(rating));
            }

            hostDTO = new HostDTO(profileDTO,
                    petDTOList,
                    host.getPrice().toBigInteger().toString(),
                    host.getPreferenceSizes(),
                    host.getAbout());
            hostDTO.setId(host.getId().toString());

            if (host.getAverageRate() != null) hostDTO.setRateAvg(host.getAverageRate().toString());

            hostDTO.setBookingDetailsDTO(getBookDetailsDTO(host));
            hostDTO.setRateDTOList(rateDTOList);
            hostReturnList.add(hostDTO);
        }

        return hostReturnList;
    }

    @Transactional
    public void editHost(EditHostDTO editHostDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        if (new BigDecimal(editHostDTO.getPrice()).intValue() <= 0) {
            throw new ValidationException(ExceptionMessages.INVALID_PRICE);
        }

        User user = userRepository.findById(userSS.getId()).orElse(new User());

        Host host = user.getHost();
        host.setPrice(new BigDecimal(editHostDTO.getPrice()));
        host.setAbout(editHostDTO.getAbout());
        List<Size> sizeList = sizeRepository.findAllByNameIn(editHostDTO.getSizeList());
        host.setPreferenceSizes(sizeList);

        user.setHost(host);

        userRepository.save(user);
    }

    public void rateHost(RateDTO rateDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Integer bookingId = Integer.parseInt(rateDTO.getBookingId());

        Booking booking = bookingRepository.findById(bookingId).orElse(new Booking());

        Rating rating = new Rating(new BigDecimal(rateDTO.getRate()), rateDTO.getFeedback());
        rating.setHost(booking.getHost());
        rating.setBooking(booking);

        booking.getHost().getRatings().add(rating);

        BigDecimal avgRating = calculateAverageRate(booking.getHost().getRatings());
        booking.getHost().setAverageRate(avgRating);

        booking.setRating(rating);

        bookingRepository.save(booking);
    }

    private BigDecimal calculateAverageRate(List<Rating> hostRatings) {
        double sumRatings = 0;
        for (Rating rating : hostRatings) {
            sumRatings += rating.getRate().longValue();
        }
        double avgRatings = sumRatings / hostRatings.size();
        return new BigDecimal(avgRatings);
    }

    private BookingDetailsDTO getBookDetailsDTO(Host host) {
        UserSS userSS = UserService.getUserAuthentication();
        if (userSS != null) {
            User user = userRepository.findById(userSS.getId()).orElse(new User());

            List<Booking> userAndHostBookings = bookingRepository.findBookingsByHostAndUser(host, user);
            for (Booking booking : userAndHostBookings) {
                if (!BookingStatusEnum.FINALIZED.name().equals(booking.getStatus()) && !BookingStatusEnum.REFUSED.name().equals(booking.getStatus())) {
                    return new BookingDetailsDTO(booking);
                }
            }
        }
        return null;
    }
}
