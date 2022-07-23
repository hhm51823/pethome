package cn.raths.serve.service;

import cn.raths.order.dto.OrderConfirmationDto;
import cn.raths.serve.domain.OrderProduct;
import cn.raths.basic.service.IBaseService;
import cn.raths.serve.dto.HandlerProduceOrderDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
public interface IOrderProductService extends IBaseService<OrderProduct> {

    void reject(Long produceOrderId);

    void accept(HandlerProduceOrderDto handlerProduceOrderDto);

    void confirm(OrderConfirmationDto orderConfirmationDto);

    void handleCancel(Long id);
}
