package br.com.bonnepet.service;

import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.input.PetDTO;
import br.com.bonnepet.repository.PetRepository;
import br.com.bonnepet.repository.UserRepository;
import br.com.bonnepet.security.UserSS;
import br.com.bonnepet.service.exception.ExceptionMessages;
import com.amazonaws.services.licensemanager.model.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private S3Service s3Service;

    public PetDTO insertPet(PetDTO petDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }
        User user = userRepository.findById(userSS.getId()).orElse(new User());

        Pet pet = new Pet(petDTO, user);

        pet = petRepository.save(pet);

        return new PetDTO(pet);
    }

    public List<PetDTO> getAllPets() {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }
        User user = userRepository.findById(userSS.getId()).orElse(new User());

        List<PetDTO> petList = new ArrayList<>();
        for (Pet pet : user.getPets()) {
            petList.add(new PetDTO(pet));
        }

        return petList;
    }

    public void uploadPetPicture(Integer id, MultipartFile multipartFile) {
        Optional<Pet> pet = petRepository.findById(id);

        if (pet.isPresent()) {
            String urlImage;
            urlImage = s3Service.uploadFile(false, pet.get().getId(), multipartFile);
            pet.get().setPictureUrl(urlImage);
            petRepository.save(pet.get());
        }
    }
}
