package br.com.bonnepet.dto;

import br.com.bonnepet.domain.Host;
import br.com.bonnepet.domain.Size;
import br.com.bonnepet.service.exception.ExceptionMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EditHostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String id;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String price;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private List<String> sizeList;

    @NotEmpty(message = ExceptionMessages.REQUIRED_FIELD)
    private String about;

    public EditHostDTO(Host host) {
        id = host.getId().toString();
        price = host.getPrice().toString();
        sizeList = getSizeList(host);
        about = host.getAbout();
    }

    private List<String> getSizeList(Host host) {
        List<String> sizeList = new ArrayList<>();
        for (Size size : host.getPreferenceSizes()) {
            sizeList.add(size.getName());
        }
        return sizeList;
    }
}
