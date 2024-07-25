package marko.mvs.gdv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmContractPriceDto {
    private String priceNet;
    private String currency;
    private String startDate;
}
