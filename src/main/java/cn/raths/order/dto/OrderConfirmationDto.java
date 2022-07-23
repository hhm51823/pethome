package cn.raths.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderConfirmationDto {

    private Long id;
    private Integer payType;
    private BigDecimal money;
}
