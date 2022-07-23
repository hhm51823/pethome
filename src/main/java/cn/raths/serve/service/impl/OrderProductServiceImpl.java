package cn.raths.serve.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.utils.CodeGenerateUtils;
import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.order.dto.OrderConfirmationDto;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.serve.domain.OrderProduct;
import cn.raths.serve.dto.HandlerProduceOrderDto;
import cn.raths.serve.mapper.OrderProductMapper;
import cn.raths.serve.service.IOrderProductService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct> implements IOrderProductService {

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Override
    public void reject(Long produceOrderId) {
        // 清空寻主消息的店铺id
        // 先查询订单是否存在
        OrderProduct orderProduct = orderProductMapper.loadById(produceOrderId);
        if(orderProduct.getState() != 0){
            throw new BusinessException("订单已失效");
        }
        orderProduct.setState(-3);
        orderProductMapper.update(orderProduct);
    }

    @Override
    public void accept(HandlerProduceOrderDto handlerProduceOrderDto) {
        // 先判断订单是否还存在，SQL中判断了订单不能为-1.为-1的订单可以继续接单
        OrderProduct orderProduct = orderProductMapper.loadById(handlerProduceOrderDto.getProduceOrderId());

        if(orderProduct == null || orderProduct.getState() != 0){
            throw new BusinessException("订单已失效");
        }

        // 生成支付订单编号
        String paySn = CodeGenerateUtils.generateOrderSn(handlerProduceOrderDto.getEmployeeId());
        orderProduct.setPaySn(paySn);
        orderProduct.setEmployeeId(handlerProduceOrderDto.getEmployeeId());
        orderProductMapper.update(orderProduct);
    }

    @Override
    public void confirm(OrderConfirmationDto orderConfirmationDto) {
        OrderProduct orderProduct = orderProductMapper.loadById(orderConfirmationDto.getId());
        if(orderProduct.getState() != 0){
           throw new BusinessException("当前状态不能修改");
        }else{
            orderProduct.setState(1);
            orderProduct.setPrice(orderConfirmationDto.getMoney());
            orderProductMapper.update(orderProduct);
        }


    }

    @Override
    public void handleCancel(Long id) {
        // 修改订单状态
        OrderProduct orderProduct = orderProductMapper.loadById(id);
        if(orderProduct.getState() != 0){
            throw new BusinessException("当前状态不能修改");
        }else{
            orderProduct.setState(-4);
            orderProductMapper.update(orderProduct);
        }
    }
}
