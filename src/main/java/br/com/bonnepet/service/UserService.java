package br.com.bonnepet.service;

import br.com.bonnepet.domain.Address;
import br.com.bonnepet.domain.City;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.*;
import br.com.bonnepet.helper.DateHelper;
import br.com.bonnepet.repository.CityRepository;
import br.com.bonnepet.repository.UserRepository;
import br.com.bonnepet.security.UserSS;
import br.com.bonnepet.service.exception.ExceptionMessages;
import br.com.bonnepet.service.exception.ValidationException;
import com.amazonaws.services.licensemanager.model.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    /**
     * Nome da pasta e arquivo de imagem no AWS S3
     */
    private static final String USER_PROFILE_IMAGE_FILE = "user-profile/user";

    public NewUserDTO insertUser(NewUserDTO newUserDTO) {
        if (userRepository.findByEmail(newUserDTO.getEmail()) != null) {
            throw new ValidationException(ExceptionMessages.EMAIL_EXIST);
        }

        AddressDTO addressDTO = newUserDTO.getAddressDTO();

        City city = cityRepository.findByName(addressDTO.getCity());

        Address address = new Address(addressDTO.getCep(), addressDTO.getDistrict(), addressDTO.getStreet(),
                addressDTO.getNumber(), city);

        String passwordEncoded = passwordEncoder.encode(newUserDTO.getPassword());

        User user = new User(null, newUserDTO.getEmail(), passwordEncoded, newUserDTO.getName(),
                DateHelper.parseToDate(newUserDTO.getBirthDate()), newUserDTO.getTelephone(),
                newUserDTO.getCellphone(), address);

        user = userRepository.save(user);

        return new NewUserDTO(user);
    }

    public EditProfileDTO updateUser(EditProfileDTO editProfileDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        User user = userRepository.findById(userSS.getId()).orElse(new User());
        AddressDTO addressDTO = editProfileDTO.getAddressDTO();
        City city = cityRepository.findByName(addressDTO.getCity());
        Address address = new Address(addressDTO.getCep(), addressDTO.getDistrict(), addressDTO.getStreet(),
                addressDTO.getNumber(), city);

        user.setName(editProfileDTO.getUserName());
        user.setBirthDate(DateHelper.parseToDate(editProfileDTO.getBirthDate()));
        user.setTelephone(editProfileDTO.getTelephone());
        user.setCellphone(editProfileDTO.getCellphone());
        user.setAddress(address);

        user = userRepository.save(user);

        return new EditProfileDTO(user);
    }

    public PictureDTO uploadProfilePicture(Integer id, MultipartFile multipartFile) {
        Optional<User> user = userRepository.findById(id);
        String urlImage = "";

        if (user.isPresent()) {
            String fileName = USER_PROFILE_IMAGE_FILE + user.get().getId();
            urlImage = s3Service.uploadFile(imageService.getProfileFormat(multipartFile), fileName);

            user.get().setPictureUrl(urlImage);
            userRepository.save(user.get());
        }
        return new PictureDTO(urlImage);
    }

    public ProfileDTO getUserProfile() {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        User user = userRepository.findById(userSS.getId()).orElse(null);
        assert user != null;

        ProfileDTO profileDTO = new ProfileDTO(user);

        if (user.getHost() != null) {
            EditHostDTO editHostDTO = new EditHostDTO(user.getHost());
            profileDTO.setEditHostDTO(editHostDTO);
        }

        return profileDTO;
    }

    public static UserSS getUserAuthentication() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
