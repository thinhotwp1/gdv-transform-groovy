import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper

class JsonTransformer {
    static String convert(String inputJson) {
        def input = new JsonSlurper().parseText(inputJson)

        def output = [
                Contracts: input."Bestandsdaten oder Produktinfo Investment"."Vertrag".collect { contract ->
                    [
                            "PersonDetail"      : contract.Partner.collect { partner ->
                                [
                                        "PmPersonDetailDto": [
                                                "BirthDate": partner."0100"."1".Geburtsdatum,
                                                "Name 2"   : partner."0100"."1"."Name 2",
                                                "Titel"    : partner."0100"."1"."Titel"
                                        ],
                                        "PmPersonDto"      : [
                                                "firstName": partner."0100"."1"."Name 1",
                                                "lastName" : partner."0100"."1"."Name 3"
                                        ],
                                        "AdAddressDto"     : [
                                                "postalCode": partner."0100"."1"."Postleitzahl",
                                                "city"      : partner."0100"."1"."Ort",
                                                "street"    : partner."0100"."1"."Stra�e"
                                        ]
                                ]
                            },

                            "CmContractDto"     : [
                                    "contractIdentifier": contract."0200"."1"."Satzanfang"."Versicherungsschein-Nummer",
                                    "startDate"         : contract."0200"."1"."Vertragsbeginn",
                                    "plannedEndDate"    : contract."0200"."1"."Vertragsablauf"
                            ],
                            "CmContractPriceDto": [
                                    "priceNet" : contract."0200"."1"."Gesamtbeitrag (Netto) in W�hrungseinheiten",
                                    "currency" : contract."0200"."1"."W�hrungsschl�ssel",
                                    "startDate": contract."0200"."1"."Antragsdatum"
                            ]

                    ]
                }
        ]

        ObjectMapper mapper = new ObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output)

    }
}
