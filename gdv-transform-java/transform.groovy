import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.util.ConfigSlurper

class JsonTransformer {
    static String convert(String inputJson) {
        def input = new JsonSlurper().parseText(inputJson)

        // Load configuration from file
        def config = new ConfigSlurper().parse(new File('config.groovy').toURI().toURL())

        // Retrieve the output configuration closure
        def outputClosure = config.output

        // Apply the configuration closure to generate output
        def output = outputClosure(input)

        // Return result
        ObjectMapper mapper = new ObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output)
    }
}
