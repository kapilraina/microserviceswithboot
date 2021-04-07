package com.ms.bootcamp.discountserviceprocessor.ws;

public class DiscountByInstanceSocksPayload {

	private String category;
	private String discountApplied;
	private long timestamp;
	private String formattedTimestamp;


	public DiscountByInstanceSocksPayload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountByInstanceSocksPayload(String category, String discountApplied, long timestamp, String formattedTimestamp) {
		this.category = category;
		this.discountApplied = discountApplied;
		this.timestamp = timestamp;
		this.formattedTimestamp = formattedTimestamp;
	}

	@Override
	public String toString() {
		return "DiscountByInstanceSocksPayload{" +
				"category='" + category + '\'' +
				", discountApplied='" + discountApplied + '\'' +
				", timestamp=" + timestamp +
				", formattedTimestamp='" + formattedTimestamp + '\'' +
				'}';
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(String discountApplied) {
		this.discountApplied = discountApplied;
	}


	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getFormattedTimestamp() {
		return formattedTimestamp;
	}

	public void setFormattedTimestamp(String formattedTimestamp) {
		this.formattedTimestamp = formattedTimestamp;
	}


}
