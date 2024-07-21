package marko.mvs.gdv.process;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

public class CamelProcessor {
    private static final Logger logger = Logger.getLogger(CamelProcessor.class.getName());

    public String processJson(String inputJson) throws Exception {
        CamelContext context = new DefaultCamelContext();
        final StringBuilder result = new StringBuilder();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .log("Processing JSON data")
                        .unmarshal().json()
                        .process(new GermanToEnglishProcessor())
                        .marshal().json()
                        .process(exchange -> result.append(exchange.getIn().getBody(String.class)));
            }
        });

        context.start();
        context.createProducerTemplate().sendBody("direct:start", inputJson);
        context.stop();

        return result.toString();
    }
}
