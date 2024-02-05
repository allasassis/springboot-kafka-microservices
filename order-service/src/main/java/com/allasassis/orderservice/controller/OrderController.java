package com.allasassis.orderservice.controller;

import com.allasassis.basedomains.dto.Order;
import com.allasassis.basedomains.dto.OrderEvent;
import com.allasassis.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent oe = new OrderEvent();
        oe.setStatus("PENDING");
        oe.setMessage("Order status is in pending state");
        oe.setOrder(order);
        orderProducer.sendMessage(oe);
        return "Order placed successfully!";
    }
}
