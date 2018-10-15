package simple.boot.opentracingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/microservice2")
    public String microservice2() {
        return "Hello from MicroService2!";
    }

    @RequestMapping("/microservice1")
    public String microservice1() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/microservice2", String.class);
        return "Information I got from MicroService2 + " + response.getBody();
    }
}
