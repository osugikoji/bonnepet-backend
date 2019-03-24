package br.com.bonnepet.service;

import br.com.bonnepet.domain.Address;
import br.com.bonnepet.domain.City;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.input.NewUserDTO;
import br.com.bonnepet.helper.DateHelper;
import br.com.bonnepet.repository.CityRepository;
import br.com.bonnepet.repository.UserRepository;
import br.com.bonnepet.service.exception.ExceptionMessages;
import br.com.bonnepet.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    public User insertUser(NewUserDTO newUserDTO) {

        if (userRepository.findByEmail(newUserDTO.getEmail()) != null) {
            throw new ValidationException(ExceptionMessages.EMAIL_EXIST);
        }
        City city = cityRepository.findByName(newUserDTO.getCity());

        Address address = new Address(newUserDTO.getCep(), newUserDTO.getDistrict(), newUserDTO.getStreet(),
                newUserDTO.getNumber(), city);

        String passwordEncoded = passwordEncoder.encode(newUserDTO.getPassword());

        User user = new User(newUserDTO.getEmail(), passwordEncoded, newUserDTO.getName(),
                DateHelper.parseToDate(newUserDTO.getBirthDate()), newUserDTO.getTelephone(),
                newUserDTO.getCellphone(), address);

        return userRepository.save(user);
    }
}
