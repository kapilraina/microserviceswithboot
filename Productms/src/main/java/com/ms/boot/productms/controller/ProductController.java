package com.ms.boot.productms.controller;

import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductDTO;
import com.ms.boot.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/product")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			return new ResponseEntity<Product>(p, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductv1(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV1(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductv2(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV2(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v3/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductv3(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV3(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}


	@GetMapping(path = "/product/v4/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO>
	getProductv4(@PathVariable Integer id) {
		Product p =
				productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto =
					productService.applyDiscountV4(p);
			return new
					ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new
					ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v5/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductv5(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV5(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

}
