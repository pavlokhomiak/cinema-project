package com.dev.cinema.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private List<TicketResponseDto> ticketList;
    private LocalDateTime orderDate;
    private Long userId;
}
