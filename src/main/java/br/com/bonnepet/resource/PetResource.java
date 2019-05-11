package br.com.bonnepet.resource;

import br.com.bonnepet.dto.input.PetDTO;
import br.com.bonnepet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pets")
public class PetResource {

    @Autowired
    private PetService petService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertPet(@Valid @RequestBody PetDTO petDTO) {
        PetDTO pet = petService.insertPet(petDTO);
        return ResponseEntity.ok(pet.getId());
    }

    @PostMapping("/{id}/picture")
    public ResponseEntity<Void> uploadPetPicture(@RequestParam(name = "file") MultipartFile file, @PathVariable Integer id) {
        petService.uploadPetPicture(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> petList = petService.getAllPets();
        return ResponseEntity.ok(petList);
    }

}
