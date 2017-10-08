package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@ServletComponentScan
@SpringBootApplication
public class PortalApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PortalApplication.class, args);
	}

}
