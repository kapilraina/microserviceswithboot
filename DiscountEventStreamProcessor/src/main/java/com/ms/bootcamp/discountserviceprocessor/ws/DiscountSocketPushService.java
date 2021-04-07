package com.ms.bootcamp.discountserviceprocessor.ws;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ms.bootcamp.discountserviceprocessor.stream.processors.AggregatedWindowedDiscount;
import com.ms.bootcamp.discountserviceprocessor.stream.processors.DiscountByInstance;

@Service
public class DiscountSocketPushService {

	@Autowired
	private SimpMessagingTemplate template;
	private SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss");
	private DecimalFormat df = new DecimalFormat("0.00");

	public void pipeToWebAggSocket(AggregatedWindowedDiscount awd) {

		AggregatedWindowedDiscountSocksPayload awdsp = new AggregatedWindowedDiscountSocksPayload();
		awdsp.setCategory(awd.getCategory());
		awdsp.setWindowTotal(df.format(awd.getWindowTotal()));
		awdsp.setWindowStart(sdf.format(awd.getWindowStart()));
		awdsp.setWindowEnd(sdf.format(awd.getWindowEnd()));
		awdsp.setWindowStartMills(awd.getWindowStartMills());
		awdsp.setWindowEndMills(awd.getWindowEndMills());
		System.out.println("pipeToWebAggSocket :" + awdsp);
		template.convertAndSend("/topic/messages", awdsp);
	}

	public void pipeToWebInstanceSocket(DiscountByInstance wdi) {

		DiscountByInstanceSocksPayload wdisp = new DiscountByInstanceSocksPayload();
		wdisp.setCategory(wdi.getCategory());
		wdisp.setDiscountApplied(df.format(wdi.getDiscountApplied()));
		wdisp.setTimestamp(wdi.getTimestamp());
		wdisp.setFormattedTimestamp(sdf.format(wdi.getTimestamp()));
		System.out.println("pipeToWebInstanceSocket :" + wdisp);
		template.convertAndSend("/topic/messages_i", wdisp);
	}

}
