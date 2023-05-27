package com.kodlamaio.filterservice;

import com.kodlamaio.commonpackage.utils.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Filter.SERVICEBASEPACKAGE})
public class FilterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilterServiceApplication.class, args);
	}

}
