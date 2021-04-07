package com.ms.boot.discountms;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.boot.discountms.audit.AuditService;
import com.ms.boot.discountms.audit.stream.DiscountStreamService;
import com.ms.boot.discountms.metrics.DiscountMetricsService;

@RestController
@RefreshScope
public class DiscountController {

	@Autowired
	DiscountDataMap discountDataMap;

	private static Logger log = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	AuditService auditService;

	@Autowired
	DiscountStreamService discountStreamService;

	@Autowired
	DiscountMetricsService discountMetricsService;

	@PostConstruct
	public void initBean() {

		/*
		 * discountDataMap.getDiscountMap().put(ProductCategory.FURNISHING, 1.0);
		 * discountDataMap.getDiscountMap().put(ProductCategory.KITCHENELECTRONIC, 3.0);
		 * discountDataMap.getDiscountMap().put(ProductCategory.MEDICALEQUIPMENT, 7.0);
		 * discountDataMap.getDiscountMap().put(ProductCategory.MOBILEPHONE, 5.0);
		 * discountDataMap.getDiscountMap().put(ProductCategory.MUSICINSTRUMENT, 9.0);
		 * discountDataMap.getDiscountMap().put(ProductCategory.TOY, 2.0);
		 */

	}

	@RequestMapping(value = "/caldisc", method = RequestMethod.POST)
	public DiscountResponse calculateDiscount(@RequestBody DiscountRequest request) {
		log.info(request.toString());
		double fixedCategoryDiscount = discountDataMap.getDiscountMap().get(request.getCategory());
		double onSpotDiscount = (new Random()).nextInt(15);
		double discountper = fixedCategoryDiscount + onSpotDiscount;
		double drp = Math.ceil(request.getMrp() - ((discountper / 100) * request.getMrp()));
		DiscountResponse response = new DiscountResponse(request.getCategory(), request.getMrp(), drp,
				fixedCategoryDiscount, onSpotDiscount, System.currentTimeMillis());
		pubAuditEvent(response);
		pubAuditEventStream(response);
		createAndLogMetrics(response);
		return response;
	}

	public void pubAuditEvent(DiscountResponse response) {
		auditService.pubAuditEvent(response);
	}

	public void pubAuditEventStream(DiscountResponse response) {
		discountStreamService.pubAuditEventStream(response);
	}

	public void createAndLogMetrics(DiscountResponse response) {
		discountMetricsService.createAndLogMetrics(response);
	}

}
