package cn.raths.serve.dto;

import cn.raths.pet.domain.Pet;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseDto {

    private Long userId;
    private String digest;
    private BigDecimal price;
    private Long productId;
    private Long shopId;
    private String address;
    private Pet pet;
}
