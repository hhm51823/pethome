package cn.raths.order.service;

import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.basic.service.IBaseService;
import cn.raths.order.dto.OrderConfirmationDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-19
 */
public interface IOrderPetAcquisitionService extends IBaseService<OrderPetAcquisition> {

    void confirm(OrderConfirmationDto orderConfirmationDto);

    void handleCancel(Long id);
}
