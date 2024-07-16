import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

class JsonTransformer {
    static String convert(String inputJson) {
        def input = new JsonSlurper().parseText(inputJson)

        def output = [
                "1": [
                        RecordType            : input."1".Satzanfang.Satzart,
                        InsuranceCompanyNumber: input."1".Satzanfang."VU-Nummer",
                        Sender                : input."1".Satzanfang.Absender,
                        Recipient             : input."1".Satzanfang.Adressat,
                        CreationDatePeriod    : input."1".Satzanfang."Erstellungs-Datum- Zeitraum vom- Zeitraum bis",
                        BranchOrAgent         : input."1".Satzanfang."Gesch�ftsstelle/Vermittler",
                        newField              : "new_value"
                ]
        ]

        return new JsonBuilder(output).toPrettyString()
    }
}
