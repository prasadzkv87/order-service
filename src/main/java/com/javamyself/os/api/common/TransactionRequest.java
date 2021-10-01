package com.javamyself.os.api.common;

import com.javamyself.os.api.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

	private PaymentDto paymentDto;
	private Order order;

}
