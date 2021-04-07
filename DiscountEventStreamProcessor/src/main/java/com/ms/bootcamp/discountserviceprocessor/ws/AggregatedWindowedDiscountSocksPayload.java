package com.ms.bootcamp.discountserviceprocessor.ws;

public class AggregatedWindowedDiscountSocksPayload {
	private String category;
	private String windowTotal;
	private String windowStart;
	private String windowEnd;
	private long windowStartMills;
	private long windowEndMills;

	public AggregatedWindowedDiscountSocksPayload(String category, String windowTotal, String windowStart, String windowEnd, long windowStartMills, long windowEndMills) {
		this.category = category;
		this.windowTotal = windowTotal;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
		this.windowStartMills = windowStartMills;
		this.windowEndMills = windowEndMills;
	}

	public AggregatedWindowedDiscountSocksPayload() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AggregatedWindowedDiscountSocksPayload{" +
				"category='" + category + '\'' +
				", windowTotal='" + windowTotal + '\'' +
				", windowStart='" + windowStart + '\'' +
				", windowEnd='" + windowEnd + '\'' +
				", windowStartMills=" + windowStartMills +
				", windowEndMills=" + windowEndMills +
				'}';
	}

	public void setWindowStartMills(long windowStartMills) {
		this.windowStartMills = windowStartMills;
	}

	public long getWindowEndMills() {
		return windowEndMills;
	}

	public void setWindowEndMills(long windowEndMills) {
		this.windowEndMills = windowEndMills;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWindowTotal() {
		return windowTotal;
	}
	public void setWindowTotal(String windowTotal) {
		this.windowTotal = windowTotal;
	}
	public String getWindowStart() {
		return windowStart;
	}
	public void setWindowStart(String windowStart) {
		this.windowStart = windowStart;
	}
	public String getWindowEnd() {
		return windowEnd;
	}
	public void setWindowEnd(String windowEnd) {
		this.windowEnd = windowEnd; 
	}
	
	

}
