package marko.mvs.gdv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDetail {
    private List<PersonDetail> personDetail;
    private CmContractDto cmContractDto;
    private CmContractPriceDto cmContractPriceDto;
}
