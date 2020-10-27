package com.dev.cinema.util.mapper;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.dto.OrderResponseDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private TicketMapper ticketMapper;

    public OrderMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setUserId(order.getUser().getId());
        dto.setTicketList(order.getTickets().stream()
                .map(ticketMapper::toTicketResponseDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
