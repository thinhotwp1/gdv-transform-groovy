package marko.mvs.gdv;

import marko.mvs.gdv.process.CamelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private CamelProcessor camelProcessor;

    @PostMapping("/process")
    public String process(@RequestBody String inputJson) throws Exception {
        return camelProcessor.processJson(inputJson);
    }
}
