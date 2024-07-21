package marko.mvs.gdv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PmPersonDetailDto {
    private String birthDate;
    private String name2;
    private String title;
}
