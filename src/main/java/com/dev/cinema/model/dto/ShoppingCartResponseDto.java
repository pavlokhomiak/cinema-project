package com.dev.cinema.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private List<TicketResponseDto> ticketList;
    private Long userId;
}
