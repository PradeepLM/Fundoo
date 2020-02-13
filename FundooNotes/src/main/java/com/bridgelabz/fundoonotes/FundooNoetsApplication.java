package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*is equivalent to using @Configuration, @EnableAutoConfiguration,
and @ComponentScan with their default attributes*/
@SpringBootApplication
public class FundooNoetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNoetsApplication.class, args);
		System.out.println("Helllo");
	}

}
