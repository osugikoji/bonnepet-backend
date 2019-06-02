package br.com.bonnepet.service;

import br.com.bonnepet.domain.Behaviour;
import br.com.bonnepet.domain.Pet;
import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.PetDTO;
import br.com.bonnepet.helper.DateHelper;
import br.com.bonnepet.repository.BehaviourRepository;
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
    private BehaviourRepository behaviourRepository;

    @Autowired
    private S3Service s3Service;

    public PetDTO insertPet(PetDTO petDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }
        User user = userRepository.findById(userSS.getId()).orElse(new User());

        Pet pet = new Pet(petDTO, user);

        List<Behaviour> behaviourList = behaviourRepository.findAllByNameIn(petDTO.getBehaviours());

        pet.getBehaviours().addAll(behaviourList);

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

    public PetDTO updatePet(PetDTO petDTO) {
        UserSS userSS = UserService.getUserAuthentication();

        if (userSS == null) {
            throw new AuthorizationException(ExceptionMessages.ACCESS_DENIED);
        }

        Pet pet = petRepository.findById(Integer.parseInt(petDTO.getId())).orElse(new Pet());

        pet.setName(petDTO.getName());
        pet.setBirthDate(DateHelper.parseToDate(petDTO.getBirthDate()));
        pet.setBreed(petDTO.getBreed());
        pet.setSize(petDTO.getSize());
        pet.setObservations(petDTO.getObservations());

        List<Behaviour> behaviourList = behaviourRepository.findAllByNameIn(petDTO.getBehaviours());

        pet.setBehaviours(behaviourList);

        pet = petRepository.save(pet);

        return petDTO;
    }

    public PetDTO uploadPetPicture(Integer id, MultipartFile multipartFile) {
        Optional<Pet> pet = petRepository.findById(id);
        PetDTO petDTO = new PetDTO();

        if (pet.isPresent()) {
            String urlImage;
            urlImage = s3Service.uploadFile(false, pet.get().getId(), multipartFile);
            pet.get().setPictureUrl(urlImage);
            petDTO = new PetDTO(petRepository.save(pet.get()));
        }
        return petDTO;
    }
}
