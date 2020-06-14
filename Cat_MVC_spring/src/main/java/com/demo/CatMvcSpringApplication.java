package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.demo.dao.ProduitRepository;
import com.demo.entities.Produit;

@SpringBootApplication
public class CatMvcSpringApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(CatMvcSpringApplication.class, args);
		ProduitRepository produitRep = context.getBean(ProduitRepository.class);
		produitRep.save(new Produit("Lg 45", 500, 2));
		produitRep.save(new Produit("samsung 8", 800, 2));
		produitRep.save(new Produit("iphone 10", 1000, 5));
		produitRep.save(new Produit("Imprimante ", 900, 2));
		
		produitRep.findAll().forEach(p->System.out.println(p.getDesignation()));
		
	}

}
