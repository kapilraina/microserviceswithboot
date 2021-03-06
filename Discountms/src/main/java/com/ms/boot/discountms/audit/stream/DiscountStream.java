package com.ms.boot.discountms.audit.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DiscountStream {

	
	final String OUTPUT = "discountstream-out";


	@Output(OUTPUT)
	MessageChannel outboundDiscountStream();

}
