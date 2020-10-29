package com.dev.cinema.controller;

import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.OrderRequestDto;
import com.dev.cinema.model.dto.OrderResponseDto;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.mapper.OrderMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private OrderMapper orderMapper;

    public OrderController(OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           UserService userService,
                           OrderMapper orderMapper) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/complete")
    public void completeOrder(@RequestBody @Valid OrderRequestDto dto) {
        User user = userService.findByEmail(dto.getUserEmail()).get();
        List<Ticket> ticketList = shoppingCartService.getByUser(user).getTickets();
        orderService.completeOrder(ticketList, user);
    }

    @GetMapping
    public List<OrderResponseDto> getOrdersByUserId(
            Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String email = principal.getUsername();
        User user = userService.findByEmail(email).get();
        return orderService.getOrderHistory(user).stream()
                .map(orderMapper::toOrderResponseDto)
                .collect(Collectors.toList());
    }
}
