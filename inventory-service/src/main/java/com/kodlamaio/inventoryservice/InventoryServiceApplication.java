package com.kodlamaio.inventoryservice;

import com.kodlamaio.commonpackage.utils.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Inventory.ServiceBasePackage})
public class InventoryServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        String[] name = new String[] {"K", "U", "D", "R", "E", "T"};
        for (String letter : name) {
            System.out.printf("\r%s", letter);
            Thread.sleep(750);
        }
        SpringApplication.run(InventoryServiceApplication.class, args);

    }

}
