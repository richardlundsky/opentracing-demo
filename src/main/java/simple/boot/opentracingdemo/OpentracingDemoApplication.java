package simple.boot.opentracingdemo;

import io.jaegertracing.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpentracingDemoApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	public io.opentracing.Tracer jaegerTracer() {
		return Configuration.fromEnv().
				getTracer();
	}

	public static void main(String[] args) {
		SpringApplication.run(OpentracingDemoApplication.class, args);
	}
}
