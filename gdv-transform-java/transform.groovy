import groovy.json.JsonSlurper
import com.fasterxml.jackson.databind.ObjectMapper

class JsonTransformer {
    static String convert(String inputJson) {
        def input = new JsonSlurper().parseText(inputJson)

        def output = [
                "Partner": input.Partner.collect { partner ->
                    [
                            "0100": [
                                    "1": [
                                            "RecordType"                : partner."0100"."1".Satzanfang.Satzart,
                                            "InsuranceCompanyNumber"    : partner."0100"."1".Satzanfang."VU-Nummer",
                                            "BundleIndicator"           : partner."0100"."1".Satzanfang.Bündelungskennzeichen,
                                            "Division"                  : partner."0100"."1".Satzanfang.Sparte,
                                            "InsurancePolicyNumber"     : partner."0100"."1".Satzanfang."Versicherungsschein-Nummer",
                                            "SequenceNumber"            : partner."0100"."1".Satzanfang.Folgenummer,
                                            "BranchOrAgent"             : partner."0100"."1".Satzanfang."Geschäftsstelle / Vermittler",
                                            "SalutationKey"             : partner."0100"."1".Anredeschlüssel,
                                            "Name 1"                    : partner."0100"."1"."Name 1",
                                            "Name 2"                    : partner."0100"."1"."Name 2",
                                            "Name 3"                    : partner."0100"."1"."Name 3",
                                            "Title"                     : partner."0100"."1".Titel,
                                            "CountryCode"               : partner."0100"."1".Länderkennzeichen,
                                            "PostalCode"                : partner."0100"."1".Postleitzahl,
                                            "City"                      : partner."0100"."1".Ort,
                                            "Street"                    : partner."0100"."1".Straße,
                                            "P.O.Box"                   : partner."0100"."1".Postfach,
                                            "DateOfBirth"               : partner."0100"."1".Geburtsdatum,
                                            "Nationality"               : partner."0100"."1".Staatsangehörigkeit,
                                            "AddressCode"               : partner."0100"."1".Adresskennzeichen,
                                            "CaseReference"             : partner."0100"."1"."Aktenzeichen des Sicherungsgläubigers",
                                            "TargetGroupCode"           : partner."0100"."1".Zielgruppenschlüssel,
                                            "NonSupervisedPolicyholder" : partner."0100"."1"."Aufsichtsfreier Versicherungsnehmer (Großrisiken)",
                                            "PostalCodeIndicator"       : partner."0100"."1"."postalisches Kennzeichen",
                                            "Gender"                    : partner."0100"."1".Geschlecht,
                                            "RecordEnd"                 : partner."0100"."1".Satzende,
                                            "newField"                  : "new_value"
                                    ],
                                    "2": [
                                            "RecordType"                : partner."0100"."2".Satzanfang.Satzart,
                                            "InsuranceCompanyNumber"    : partner."0100"."2".Satzanfang."VU-Nummer",
                                            "BundleIndicator"           : partner."0100"."2".Satzanfang.Bündelungskennzeichen,
                                            "Division"                  : partner."0100"."2".Satzanfang.Sparte,
                                            "InsurancePolicyNumber"     : partner."0100"."2".Satzanfang."Versicherungsschein-Nummer",
                                            "SequenceNumber"            : partner."0100"."2".Satzanfang.Folgenummer,
                                            "BranchOrAgent"             : partner."0100"."2".Satzanfang."Geschäftsstelle / Vermittler",
                                            "InsurerCustomerNumber"     : partner."0100"."2"."Personen-/Kundennummer des Versicherers",
                                            "AgentCustomerNumber"       : partner."0100"."2"."Personen-/Kundennummer des Vermittlers",
                                            "CustomerGroup"             : partner."0100"."2".Kundengruppe,
                                            "AccountNumber1"            : partner."0100"."2"."Kontonummer 1",
                                            "BankCode1"                 : partner."0100"."2"."Bankleitzahl 1",
                                            "DifferentAccountHolder1"   : partner."0100"."2"."Abweichender Kontoinhaber 1",
                                            "CommunicationType1"        : partner."0100"."2"."Kommunikationstyp 1",
                                            "CommunicationNumber1"      : partner."0100"."2"."Kommunikationsnummer 1",
                                            "CommunicationType2"        : partner."0100"."2"."Kommunikationstyp 2",
                                            "CommunicationNumber2"      : partner."0100"."2"."Kommunikationsnummer 2",
                                            "CommunicationType3"        : partner."0100"."2"."Kommunikationstyp 3",
                                            "CommunicationNumber3"      : partner."0100"."2"."Kommunikationsnummer 3",
                                            "CommunicationType4"        : partner."0100"."2"."Kommunikationstyp 4",
                                            "CommunicationNumber4"      : partner."0100"."2"."Kommunikationsnummer 4",
                                            "PaymentMethod"             : partner."0100"."2"."Zahlungsart /-weg",
                                            "MaritalStatus"             : partner."0100"."2".Familienstand,
                                            "RecordEnd"                 : partner."0100"."2".Satzende,
                                            "newField"                  : "new_value"
                                    ]
                            ],
                            "0342": [],
                            "0350": [],
                            "0390": []
                    ]
                },
                "0200": [
                        "1": [
                                "RecordType"                : input."0200"."1".Satzanfang.Satzart,
                                "InsuranceCompanyNumber"    : input."0200"."1".Satzanfang."VU-Nummer",
                                "BundleIndicator"           : input."0200"."1".Satzanfang.Bündelungskennzeichen,
                                "Division"                  : input."0200"."1".Satzanfang.Sparte,
                                "InsurancePolicyNumber"     : input."0200"."1".Satzanfang."Versicherungsschein-Nummer",
                                "SequenceNumber"            : input."0200"."1".Satzanfang.Folgenummer,
                                "BranchOrAgent"             : input."0200"."1".Satzanfang."Geschäftsstelle / Vermittler",
                                "CollectionType"            : input."0200"."1".Inkassoart,
                                "ContractStart"             : input."0200"."1".Vertragsbeginn,
                                "ContractEnd"               : input."0200"."1".Vertragsablauf,
                                "MainDueDate"               : input."0200"."1".Hauptfälligkeit,
                                "PaymentMethod"             : input."0200"."1".Zahlungsweise,
                                "ContractStatus"            : input."0200"."1".Vertragsstatus,
                                "ExitReason"                : input."0200"."1".Abgangsgrund,
                                "ExitDate"                  : input."0200"."1".Abgangsdatum,
                                "ChangeReason"              : input."0200"."1".Änderungsgrund,
                                "ChangeDate"                : input."0200"."1".Änderungsdatum,
                                "AFQ"                       : input."0200"."1"."A, F, B",
                                "Percentage"                : input."0200"."1"."Anteil in %",
                                "AgentOrderNumber"          : input."0200"."1"."Auftrags-Nr. des Vermittlers",
                                "CurrencyCode"              : input."0200"."1".Währungsschlüssel,
                                "TotalNetPremium"           : input."0200"."1"."Gesamtbeitrag (Netto) in Währungseinheiten",
                                "MultiPurposeField"         : input."0200"."1".Mehrzweckfeld,
                                "TaxFreeIndicator"          : input."0200"."1"."Kennzeichen Vers.-Steuer frei",
                                "InsurerCustomerNumber"     : input."0200"."1"."Personen-/Kundennummer des Versicherers",
                                "AgentCustomerNumber"       : input."0200"."1"."Personen-/Kundennummer des Vermittlers",
                                "NonSupervisedContract"     : input."0200"."1"."Aufsichtsfreier Vertrag",
                                "TaxDistribution"           : input."0200"."1"."Aufteilung Versicherungsteuer gemäß EG-Richtlinien",
                                "RemainingContractTerm"     : input."0200"."1"."Restlaufzeit des Vertrages",
                                "ContractTermDiscount"      : input."0200"."1"."Laufzeitrabatt in %",
                                "ApplicationDate"           : input."0200"."1".Antragsdatum,
                                "ReferencePolicyNumber"     : input."0200"."1"."Referenz-Versicherungsscheinnummer",
                                "SpecificationRefNumber"    : input."0200"."1"."Spezifikation der Referenz-Versicherungsscheinnummer",
                                "OrderTerm"                 : input."0200"."1".Ordnungsbegriff,
                                "DocumentCurrency"          : input."0200"."1"."Währung der Dokumente für VN",
                                "ExtensionAvailable"        : input."0200"."1"."Erweiterungssatz vorhanden",
                                "DepositWithdrawal"         : input."0200"."1"."Einzahlung / Ausschüttung",
                                "RecordEnd"                 : input."0200"."1".Satzende,
                                "newField"                  : "new_value"
                        ]
                ],
                "0342": [],
                "0350": [],
                "0390": [],
                "0300": []
        ]

        ObjectMapper mapper = new ObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output)

    }
}
