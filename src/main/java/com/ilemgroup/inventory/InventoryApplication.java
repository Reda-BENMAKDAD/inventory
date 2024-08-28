package com.ilemgroup.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ilemgroup.inventory.product.Product;
import java.util.List;;

@SpringBootApplication
@RestController
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);

	}

	@GetMapping
		public List<Product> products() {
			return List.of(new Product(30221L, "shampoing2", "shampoing head and shoulders", 40.5F));
	}

}
