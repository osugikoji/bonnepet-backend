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
            hostDTO = new HostDTO(profileDTO, petDTOList, host.getPrice().toBigInteger().toString(), host.getPreferenceSizes(), host.getAbout());
            hostDTO.setId(host.getId().toString());

            hostDTO.setBookingDetailsDTO(getBookDetailsDTO(host));
            hostReturnList.add(hostDTO);
        }

        return hostReturnList;
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
