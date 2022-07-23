package cn.raths.order.service.impl;

import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.order.dto.OrderConfirmationDto;
import cn.raths.order.mapper.OrderPetAcquisitionMapper;
import cn.raths.order.service.IOrderPetAcquisitionService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.mapper.PetMapper;
import cn.raths.pet.mapper.SearchMasterMsgMapper;
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
 * @since 2022-07-19
 */
@Service
public class OrderPetAcquisitionServiceImpl extends BaseServiceImpl<OrderPetAcquisition> implements IOrderPetAcquisitionService {

    @Autowired
    private OrderPetAcquisitionMapper orderPetAcquisitionMapper;

    @Autowired
    private PetMapper petMapper;

     @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    /**
    * @Title: confirm
    * @Description: 完成订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 18:06
    * @Parameters: [orderConfirmationDto]
    * @Return void
    */
    @Override
    public void confirm(OrderConfirmationDto orderConfirmationDto) {
        OrderPetAcquisition orderPetAcquisition = orderPetAcquisitionMapper.loadById(orderConfirmationDto.getId());
        // 修改状态为已完成
        orderPetAcquisition.setState(1);
        // 修改订单价格
        orderPetAcquisition.setPrice(orderConfirmationDto.getMoney());
        // 修改支付方式
        orderPetAcquisition.setPaytype(orderConfirmationDto.getPayType());
        orderPetAcquisitionMapper.update(orderPetAcquisition);

        // 修改pet成本价
        Pet pet = petMapper.loadById(orderPetAcquisition.getPetId());
        pet.setCostprice(orderConfirmationDto.getMoney());
        petMapper.update(pet);

        // 修改寻主信息为已完成
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.loadById(orderPetAcquisition.getSearchMasterMsgId());
        searchMasterMsg.setState(2);
        searchMasterMsgMapper.update(searchMasterMsg);
    }

    /**
    * @Title: handleCancel
    * @Description: 员工取消订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 18:17
    * @Parameters: [id]
    * @Return void
    */
    @Override
    public void handleCancel(Long id) {
        // 修改订单状态
        OrderPetAcquisition orderPetAcquisition = orderPetAcquisitionMapper.loadById(id);
        orderPetAcquisition.setState(-1);
        orderPetAcquisitionMapper.update(orderPetAcquisition);

        // 修改寻主信息shopid
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.loadById(orderPetAcquisition.getShopId());
        searchMasterMsg.setShopId(null);
        searchMasterMsgMapper.update(searchMasterMsg);

        // 删除宠物
        petMapper.remove(orderPetAcquisition.getPetId());

    }
}
