package br.com.bonnepet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {

    /**
     * The success.
     */
    private String success;

    /**
     * The message.
     */
    private String error;

}
