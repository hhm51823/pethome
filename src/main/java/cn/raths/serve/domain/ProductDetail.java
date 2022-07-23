package cn.raths.serve.domain;

import cn.raths.basic.domain.BaseDomain;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Data
public class ProductDetail extends BaseDomain{


    private Long productId;
    /**
     * 预约须知
     */
    private String orderNotice;
    /**
     * 简介
     */
    private String intro;
}
