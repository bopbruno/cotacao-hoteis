package br.com.cotacaohoteis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CotacaoHoteisApplication {

	public static void main(String[] args) {
		SpringApplication.run(CotacaoHoteisApplication.class, args);
	}

}
