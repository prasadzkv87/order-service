package com.javamyself.os.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamyself.os.api.common.PaymentDto;
import com.javamyself.os.api.common.TransactionRequest;
import com.javamyself.os.api.common.TransactionResponse;
import com.javamyself.os.api.entity.Order;
import com.javamyself.os.api.repository.OrderRepository;

@Service
@RefreshScope
public class OrderService {

	private Logger log = LoggerFactory.getLogger(OrderService.class);
			
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String PAYMENT_URL;
	
	public TransactionResponse saveOrder(TransactionRequest req) throws JsonProcessingException {

		String response = "";
		Order order = req.getOrder();
		PaymentDto payment = req.getPaymentDto();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());

		log.info("Order-Service Request : {}", new ObjectMapper().writeValueAsString(req));

		PaymentDto paymentResponse = restTemplate.postForObject(PAYMENT_URL, payment, PaymentDto.class);

		log.info("Payment service respone from OrderService Rest call : {} ", new ObjectMapper().writeValueAsString(paymentResponse));
		
		response = paymentResponse.getPaymentStatus().equals("sucess") ? "payment processing sucessfull and orderplaced"
				: "there is failure in payment api, order added to cart";
		
		
		
		orderRepository.save(order);
		return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(),
				response);

	}

}
