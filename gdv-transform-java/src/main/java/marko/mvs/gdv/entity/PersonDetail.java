package marko.mvs.gdv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDetail {
    private PmPersonDetailDto pmPersonDetailDto;
    private PmPersonDto pmPersonDto;
    private AdAddressDto adAddressDto;
}
