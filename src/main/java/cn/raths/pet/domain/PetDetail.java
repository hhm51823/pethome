package cn.raths.pet.domain;

import java.math.BigDecimal;
import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Data
public class PetDetail extends BaseDomain{


    private Long petId;
    /**
     * 领养须知
     */
    private String adoptNotice;
    /**
     * 简介
     */
    private String intro;


}
