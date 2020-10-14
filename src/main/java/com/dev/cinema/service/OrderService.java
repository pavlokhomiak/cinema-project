package com.dev.cinema.service;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(List<Ticket> tickets, User user);

    List<Order> getOrderHistory(User user);
}
