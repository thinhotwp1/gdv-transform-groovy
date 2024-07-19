package marko.mvs.gdv.entity;

import lombok.Data;

@Data
public class CmContractPriceDto {
    private String priceNet;
    private String currency;
    private String startDate;
}
