package br.com.bonnepet.resource;

import br.com.bonnepet.dto.input.EditProfileDTO;
import br.com.bonnepet.dto.output.PictureDTO;
import br.com.bonnepet.dto.output.ProfileDTO;
import br.com.bonnepet.dto.input.NewUserDTO;
import br.com.bonnepet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<NewUserDTO> insertUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        newUserDTO = userService.insertUser(newUserDTO);
        return ResponseEntity.ok(newUserDTO);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<EditProfileDTO> updateUser(@Valid @RequestBody EditProfileDTO editProfileDTO) {
        editProfileDTO = userService.updateUser(editProfileDTO);
        return ResponseEntity.ok(editProfileDTO);
    }

    @GetMapping("/getUserProfile")
    public ResponseEntity<ProfileDTO> getUserProfile() {
        ProfileDTO profileDTO = userService.getUserProfile();
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/{id}/picture")
    public ResponseEntity<PictureDTO> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file, @PathVariable Integer id) {
        PictureDTO pictureDTO = userService.uploadProfilePicture(id, file);
        return ResponseEntity.ok(pictureDTO);
    }
}
