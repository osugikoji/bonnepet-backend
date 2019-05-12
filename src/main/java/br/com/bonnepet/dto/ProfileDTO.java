package br.com.bonnepet.dto;

import br.com.bonnepet.domain.User;
import br.com.bonnepet.dto.AddressDTO;
import br.com.bonnepet.helper.DateHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {

    private String profileImageURL;

    private String email;

    private String userName;

    private String birthDate;

    private String cellphone;

    private String telephone;

    private AddressDTO addressDTO;

    public ProfileDTO(User user) {
        profileImageURL = user.getPictureUrl();
        email = user.getEmail();
        userName = user.getName();
        birthDate = DateHelper.parseToDate(user.getBirthDate());
        cellphone = user.getCellphone();
        telephone = user.getTelephone();
        addressDTO = new AddressDTO(user.getAddress());
    }
}
