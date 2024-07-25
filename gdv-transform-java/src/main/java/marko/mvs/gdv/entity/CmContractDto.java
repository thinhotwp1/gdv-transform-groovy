package marko.mvs.gdv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmContractDto {
    private String contractIdentifier;
    private String startDate;
    private String plannedEndDate;
}
