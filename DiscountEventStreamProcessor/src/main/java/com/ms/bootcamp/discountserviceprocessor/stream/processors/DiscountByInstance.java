package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.util.Date;

public class DiscountByInstance {
	private String category;
	private double discountApplied;
	private long timestamp;

	@Override
	public String toString() {
		return "DiscountByInstance{" +
				"category='" + category + '\'' +
				", discountApplied=" + discountApplied +
				", timestamp=" + timestamp +
				'}';
	}

	public DiscountByInstance(String category, double discountApplied, long timestamp) {
		this.category = category;
		this.discountApplied = discountApplied;
		this.timestamp = timestamp;
	}

	public DiscountByInstance() {
		super();
		// TODO Auto-generated constructor stub
	}





	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(double discountApplied) {
		this.discountApplied = discountApplied;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


}
