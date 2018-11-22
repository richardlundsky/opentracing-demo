package simple.boot.opentracingdemo;

import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private Tracer jaegerTracer;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/microservice2")
    //The method name is what the span will be named by default
    public String microservice2() {
        return "Baggage Items received in MicroService2: <br><br> &emsp;"+getBaggageItems();
    }

    @RequestMapping("/microservice1/{customerId}")
    //The method name is what the span will be named by default
    public String microservice1(@PathVariable("customerId") String customerId) {
        jaegerTracer.activeSpan().setTag("customerId", customerId);
        jaegerTracer.activeSpan().setBaggageItem("customerId", customerId);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/microservice2", String.class);
        return "Information I got from MicroService2: <br><br>" + response.getBody();
    }

    private Map<String,String> getBaggageItems() {
        Map<String,String> map = new HashMap<>();
        Iterable<Map.Entry<String, String>> entries = jaegerTracer.activeSpan().context().baggageItems();
        entries.forEach(e -> map.put(e.getKey(), e.getValue()));
        return map;
    }
}
