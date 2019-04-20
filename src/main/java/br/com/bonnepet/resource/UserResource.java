package br.com.bonnepet.resource;

import br.com.bonnepet.dto.ProfileDTO;
import br.com.bonnepet.dto.UserDTO;
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
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserDTO userDTO) {
        userDTO = userService.insertUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getUserProfile")
    public ResponseEntity<ProfileDTO> getUserProfile() {
        ProfileDTO profileDTO = userService.getUserProfile();
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/{id}/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file, @PathVariable Integer id) {
        userService.uploadProfilePicture(id,file);
        return ResponseEntity.noContent().build();
    }
}
