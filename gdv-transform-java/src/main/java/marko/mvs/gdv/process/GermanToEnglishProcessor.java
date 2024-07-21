package marko.mvs.gdv.process;

import com.fasterxml.jackson.databind.JsonNode;
import marko.mvs.gdv.entity.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

public class GermanToEnglishProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        JsonNode inputJson = exchange.getIn().getBody(JsonNode.class);
        JsonNode contractsNode = inputJson.path("Bestandsdaten oder Produktinfo Investment").path("Vertrag");

        List<ContractDetail> contractDetails = new ArrayList<>();

        for (JsonNode contract : contractsNode) {
            ContractDetail.ContractDetailBuilder contractDetailBuilder = ContractDetail.builder();

            // Map PersonDetail
            List<PersonDetail> personDetails = new ArrayList<>();
            for (JsonNode partner : contract.path("Partner")) {
                JsonNode personNode = partner.path("0100").path("1");

                PersonDetail.PersonDetailBuilder personDetailBuilder = PersonDetail.builder()
                        .pmPersonDetailDto(PmPersonDetailDto.builder()
                                .birthDate(personNode.path("Geburtsdatum").asText(""))
                                .name2(personNode.path("Name 2").asText(""))
                                .title(personNode.path("Titel").asText(""))
                                .build())
                        .pmPersonDto(PmPersonDto.builder()
                                .firstName(personNode.path("Name 3").asText(""))
                                .lastName(personNode.path("Name 1").asText(""))
                                .build())
                        .adAddressDto(AdAddressDto.builder()
                                .postalCode(personNode.path("Postleitzahl").asText(""))
                                .city(personNode.path("Ort").asText(""))
                                .street(personNode.path("Straße").asText(""))
                                .build());

                personDetails.add(personDetailBuilder.build());
            }
            contractDetailBuilder.personDetail(personDetails);

            // Map CmContractDto
            JsonNode contractInfoNode = contract.path("0200").path("1");
            CmContractDto cmContractDto = CmContractDto.builder()
                    .contractIdentifier(contractInfoNode.path("Satzanfang").path("Versicherungsschein-Nummer").asText(""))
                    .startDate(contractInfoNode.path("Satzanfang").path("Vertragsbeginn").asText(""))
                    .plannedEndDate(contractInfoNode.path("Satzanfang").path("Vertragsablauf").asText(""))
                    .build();
            contractDetailBuilder.cmContractDto(cmContractDto);

            // Map CmContractPriceDto
            CmContractPriceDto cmContractPriceDto = CmContractPriceDto.builder()
                    .priceNet(contractInfoNode.path("Gesamtbeitrag (Netto) in Währungseinheiten").asText(""))
                    .currency(contractInfoNode.path("Währungsschlüssel").asText(""))
                    .startDate(contractInfoNode.path("Antragsdatum").asText(""))
                    .build();
            contractDetailBuilder.cmContractPriceDto(cmContractPriceDto);

            contractDetails.add(contractDetailBuilder.build());
        }

        Contracts contracts = Contracts.builder()
                .contracts(contractDetails)
                .build();

        ContractsData outputData = ContractsData.builder()
                .contracts(contracts)
                .build();

        exchange.getIn().setBody(outputData);
    }
}
