output = { input ->
    [
            Contracts: input."Bestandsdaten oder Produktinfo Investment"."Vertrag".collect { contract ->
                [
                        "PersonDetail": contract."Partner".collect { partner ->
                            // Person part
                            [
                                    "PmPersonDetailDto": [
                                            "birthDate": partner."0100"."1"."Geburtsdatum",
                                            "name2": partner."0100"."1"."Name 2",
                                            "title": partner."0100"."1"."Titel"
                                    ],
                                    "PmPersonDto": [
                                            "firstName": partner."0100"."1"."Name 3",
                                            "lastName": partner."0100"."1"."Name 1"
                                    ],
                                    "AdAddressDto": [
                                            "postalCode": partner."0100"."1"."Postleitzahl",
                                            "city": partner."0100"."1"."Ort",
                                            "street": partner."0100"."1"."Straße"
                                    ]
                            ]
                        },

                        // Contract part
                        "CmContractDto"     : [
                                "contractIdentifier": contract."0200"."1"."Satzanfang"."Versicherungsschein-Nummer",
                                "startDate"         : contract."0200"."1"."Vertragsbeginn",
                                "plannedEndDate"    : contract."0200"."1"."Vertragsablauf"
                        ],
                        "CmContractPriceDto": [
                                "priceNet" : contract."0200"."1"."Gesamtbeitrag (Netto) in Währungseinheiten",
                                "currency" : contract."0200"."1"."Währungsschlüssel",
                                "startDate": contract."0200"."1"."Antragsdatum"
                        ]
                ]
            }
    ]
}