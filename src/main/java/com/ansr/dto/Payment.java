package com.ansr.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Payment {

/*	@Id
	private String id;*/
	
	private int sum;
	
	private Date date;
	
	public Payment() {}
	
	public Payment(int sum, Date date) {
		this.sum = sum;
		this.date = date;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "Payment [sum=" + sum + ", date=" + date + "]";
	}
	
	
}
