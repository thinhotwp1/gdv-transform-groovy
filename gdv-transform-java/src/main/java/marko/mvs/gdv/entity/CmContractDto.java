package marko.mvs.gdv.entity;

import lombok.Data;

@Data
public class CmContractDto {
    private String contractIdentifier;
    private String startDate;
    private String plannedEndDate;
}
