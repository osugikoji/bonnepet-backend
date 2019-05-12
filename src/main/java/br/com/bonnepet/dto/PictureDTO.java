package br.com.bonnepet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureDTO {

    private String imageURL;

    public PictureDTO(String url) {
        imageURL = url;
    }
}
