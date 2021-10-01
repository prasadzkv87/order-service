package com.javamyself.os.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javamyself.os.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
