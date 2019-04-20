package br.com.bonnepet.dto;

import br.com.bonnepet.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {

    private String profileURL;

    private String userName;

    public ProfileDTO(User user) {
        profileURL = user.getPictureUrl();
        userName = user.getName();
    }
}
