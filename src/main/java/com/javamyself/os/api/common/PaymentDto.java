package com.javamyself.os.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

	private int paymentId;
	private String paymentStatus;
	private String transactionId;
	private int orderId;
	private double amount;
}
