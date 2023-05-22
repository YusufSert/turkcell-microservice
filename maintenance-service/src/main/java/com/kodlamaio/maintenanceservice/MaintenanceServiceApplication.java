package com.kodlamaio.maintenanceservice;

import com.kodlamaio.commonpackage.utils.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Maintenance.SERVICEBASEPACKAGE})
public class MaintenanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaintenanceServiceApplication.class, args);
    }

}
