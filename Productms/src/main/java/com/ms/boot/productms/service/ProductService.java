package com.ms.boot.productms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ms.boot.productms.model.DiscountRequest;
import com.ms.boot.productms.model.DiscountResponse;
import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductDTO;
import com.ms.boot.productms.repo.ProductRepository;

@Component
public class ProductService {
	
	private Logger logger;

	@Autowired
	ProductRepository repo;

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	LoadBalancerClient lbClient;

	@Autowired
	DiscountServiceProxyInterface discountProxy;

	@Bean
	@LoadBalanced
	RestTemplate loadBalancedrestTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Product getProductById(Integer id) {
		Optional<Product> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			return null;
	}

	public ProductDTO applyDiscountV1(Product p) {

		DiscountRequest drequest = createDiscountRequest(p);
		List<ServiceInstance> instances = discoveryClient.getInstances("discountms");
		for (ServiceInstance instance : instances) {
			System.out.println(instance.getHost() + ":" + instance.getPort());
		}
		ServiceInstance instance = instances.get(0);
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/caldisc";

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<DiscountRequest> dre = new HttpEntity<DiscountRequest>(drequest);

		ResponseEntity<DiscountResponse> dResponseEntity = restTemplate.exchange(url, HttpMethod.POST, dre,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	public ProductDTO applyDiscountV2(Product p) {
		ServiceInstance instance = lbClient.choose("discountms");
		DiscountRequest drequest = createDiscountRequest(p);
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/caldisc";

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<DiscountRequest> dre = new HttpEntity<DiscountRequest>(drequest);

		ResponseEntity<DiscountResponse> dResponseEntity = restTemplate.exchange(url, HttpMethod.POST, dre,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	public ProductDTO applyDiscountV3(Product p) {

		DiscountRequest drequest = createDiscountRequest(p);
		String url = "http://discountms/caldisc";
		HttpEntity<DiscountRequest> dre = new HttpEntity<DiscountRequest>(drequest);

		ResponseEntity<DiscountResponse> dResponseEntity = restTemplate.exchange(url, HttpMethod.POST, dre,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	/*
	 * @HystrixCommand(fallbackMethod = "discountFallback", commandKey =
	 * "V4-CircitBreaker") public ProductDTO applyDiscountV4(Product p) {
	 * 
	 * DiscountRequest drequest = createDiscountRequest(p); String url =
	 * "http://discountms/caldisc"; HttpEntity<DiscountRequest> dre = new
	 * HttpEntity<DiscountRequest>(drequest);
	 * 
	 * ResponseEntity<DiscountResponse> dResponseEntity = restTemplate.exchange(url,
	 * HttpMethod.POST, dre, DiscountResponse.class); return
	 * ceateProductResponseDTO(dResponseEntity.getBody(), p);
	 * 
	 * }
	 */

	public ProductDTO applyDiscountV5(Product p) {

		//logger.info("Applying Discount for :" + p);
		DiscountRequest drequest = createDiscountRequest(p);
		return ceateProductResponseDTO(discountProxy.calculateDiscount(drequest), p);

	}

	public ProductDTO discountFallback(Product p) {
		DiscountResponse discountResponse = new DiscountResponse(p.getCategory(), p.getMrp(), p.getMrp(), 0.0, 0.0);
		return ceateProductResponseDTO(discountResponse, p);
	}

	private DiscountRequest createDiscountRequest(Product p) {
		return new DiscountRequest(p.getCategory(), p.getMrp());
	}

	private ProductDTO ceateProductResponseDTO(DiscountResponse discountResponse, Product p) {
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(discountResponse.getDrp());
		pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
		pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
		pdto.setId(p.getId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());

		return pdto;
	}

}
