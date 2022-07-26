package cn.raths.pet.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.impl.RocketMQProducer;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.basic.utils.CodeGenerateUtils;
import cn.raths.basic.utils.DistanceUtil;
import cn.raths.basic.utils.Point;
import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.order.mapper.OrderPetAcquisitionMapper;
import cn.raths.org.domain.Shop;
import cn.raths.org.dto.HandlerMsgDto;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.domain.SearchMasterMsgAuditLog;
import cn.raths.pet.mapper.PetMapper;
import cn.raths.pet.mapper.SearchMasterMsgAuditLogMapper;
import cn.raths.pet.mapper.SearchMasterMsgMapper;
import cn.raths.pet.service.ISearchMasterMsgService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-19
 */
@Service
public class SearchMasterMsgServiceImpl extends BaseServiceImpl<SearchMasterMsg> implements ISearchMasterMsgService {

    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Autowired
    private SearchMasterMsgAuditLogMapper searchMasterMsgAuditLogMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private OrderPetAcquisitionMapper orderPetAcquisitionMapper;

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Override
    public void publish(SearchMasterMsg searchMasterMsg) {
        // 先保存数据
        searchMasterMsgMapper.save(searchMasterMsg);
        // 审核字段
        StringBuffer str = new StringBuffer();
        str.append(searchMasterMsg.getTitle())
                .append(searchMasterMsg.getName())
                .append(searchMasterMsg.getCoatColor())
                .append(searchMasterMsg.getAddress());
        if(!BaiduAuditUtils.TextCensor(str.toString())){
            // 审核未通过
            // 修改状态为驳回
            searchMasterMsg.setState(0);
            // 添加审核日志
            SearchMasterMsgAuditLog searchMasterMsgAuditLog = new SearchMasterMsgAuditLog();
            searchMasterMsgAuditLog.setMsgId(searchMasterMsg.getId());
            searchMasterMsgAuditLog.setNote("文字审核不通过.");
            searchMasterMsgAuditLogMapper.save(searchMasterMsgAuditLog);
            // 设置note,根据审核结果填写
            searchMasterMsg.setNote("文字审核不通过");
            // 修改
            searchMasterMsgMapper.update(searchMasterMsg);
            // 可以短信或邮箱提示用户

            throw new BusinessException("文字审核不通过,已保存到个人中心的草稿箱");
        }else{
            // 找到店铺则设置状态为审核通过
            searchMasterMsg.setState(1);
            searchMasterMsg.setNote("审核通过！");
            // 审核通过
            // 查询所有店铺
            List<Shop> shops = shopMapper.loadAll();
            // 获取用户地址
            Point point = DistanceUtil.getPoint(searchMasterMsg.getAddress());
            // 寻找距离用户50公里范围内最近的店铺
            Shop shop = DistanceUtil.getNearestShop(point, shops);
            if(shop != null){
                // 设置店铺id
                searchMasterMsg.setShopId(shop.getId());
            }
            // 没有合适的店铺则放入寻主池,不设置shopId即可

            // 修改
            searchMasterMsgMapper.update(searchMasterMsg);
        }

    }

    /**
    * @Title: accept
    * @Description: 接受订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 15:13
    * @Parameters: [handlerMsgDto]
    * @Return void
    */
    @Override
    public void accept(HandlerMsgDto handlerMsgDto) {
        // 先判断订单是否还存在，SQL中判断了订单不能为-1.为-1的订单可以继续接单
        OrderPetAcquisition orderPetAcquisition =orderPetAcquisitionMapper.loadByMsgId(handlerMsgDto.getMsgId());
        if(orderPetAcquisition != null){
            throw new BusinessException("手速太慢，该订单已被抢！");
        }
        // 查出寻主消息
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.loadById(handlerMsgDto.getMsgId());
        // 生成宠物基本信息
        Pet pet = searchMasterMsg2Pet(searchMasterMsg);
        petMapper.save(pet);
        // 生成订单
        orderPetAcquisition = generateOrders(handlerMsgDto, searchMasterMsg, pet);



        orderPetAcquisitionMapper.save(orderPetAcquisition);

        // 两天（两分钟）内没处理自动放回寻主池。传递收购订单id
        rocketMQProducer.sendDelayMsg(orderPetAcquisition.getId().toString(),6);
    }

    /**
    * @Title: reject
    * @Description: 拒绝接单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 17:33
    * @Parameters: [msgId]
    * @Return void
    */
    @Override
    public void reject(Long msgId) {
        // 清空寻主消息的店铺id
        // 先查询订单是否存在
        SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.loadById(msgId);
        if(searchMasterMsg == null){
            throw new BusinessException("订单不存在");
        }
        searchMasterMsg.setShopId(null);
        searchMasterMsgMapper.update(searchMasterMsg);
        // 记录拒单日志@TODO
    }


    private OrderPetAcquisition generateOrders(HandlerMsgDto handlerMsgDto, SearchMasterMsg searchMasterMsg, Pet pet) {
        OrderPetAcquisition orderPetAcquisition = new OrderPetAcquisition();
        // 工具类生成订单编号
        String orderSn = CodeGenerateUtils.generateOrderSn(handlerMsgDto.getHandler());
        orderPetAcquisition.setOrderSn(orderSn);
        orderPetAcquisition.setDigest(handlerMsgDto.getNote());
        // 两天后过期
        orderPetAcquisition.setLastcomfirmtime(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        orderPetAcquisition.setState(0);
        orderPetAcquisition.setPrice(searchMasterMsg.getPrice());
        orderPetAcquisition.setPetId(pet.getId());
        orderPetAcquisition.setUserId(searchMasterMsg.getUserId());
        orderPetAcquisition.setShopId(searchMasterMsg.getShopId());
        orderPetAcquisition.setAddress(searchMasterMsg.getAddress());
        orderPetAcquisition.setSearchMasterMsgId(handlerMsgDto.getMsgId());
        orderPetAcquisition.setEmpId(handlerMsgDto.getHandler());
        return orderPetAcquisition;
    }


    private Pet searchMasterMsg2Pet(SearchMasterMsg searchMasterMsg) {
        Pet pet = new Pet();
        pet.setName(searchMasterMsg.getName());
        pet.setCostprice(searchMasterMsg.getPrice());
        pet.setTypeId(searchMasterMsg.getPetType());
        pet.setShopId(searchMasterMsg.getShopId());
        pet.setSearchMasterMsgId(searchMasterMsg.getId());
        return pet;
    }
}
