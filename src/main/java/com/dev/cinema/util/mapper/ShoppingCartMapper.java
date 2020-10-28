package com.dev.cinema.util.mapper;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    private TicketMapper ticketMapper;

    public ShoppingCartMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart cart) {
        ShoppingCartResponseDto dto = new ShoppingCartResponseDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getId());
        dto.setTicketList(cart.getTickets().stream()
                .map(ticketMapper::toTicketResponseDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
