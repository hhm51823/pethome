package cn.raths.basic.listener;

import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.order.mapper.OrderPetAcquisitionMapper;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.mapper.SearchMasterMsgMapper;
import io.netty.util.CharsetUtil;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rocketmq 消息监听，@RocketMQMessageListener中的selectorExpression为tag，默认为*
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "queue_test_topic",selectorExpression="*",consumerGroup = "queue_group_test")
public class RocketMQMsgListener implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderPetAcquisitionMapper orderPetAcquisitionMapper;


    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String msg = new String(body, CharsetUtil.UTF_8);
        log.info("接收到消息：{}", msg);

        // 根据传回的Id查询订单是否完成即状态是否为0，完成则不操作
        OrderPetAcquisition orderPetAcquisition = orderPetAcquisitionMapper.loadById(new Long(msg));

        // 状态为0则表示订单为取消也未完成
        if(orderPetAcquisition.getState() == 0){
            // 未完成则将状态改为取消且将寻主消息店铺id清空放回寻主池
            SearchMasterMsg searchMasterMsg = searchMasterMsgMapper.loadById(orderPetAcquisition.getSearchMasterMsgId());
            searchMasterMsg.setShopId(null);
            searchMasterMsgMapper.update(searchMasterMsg);

            // 修改收购订单状态
            orderPetAcquisition.setState(-1);
            orderPetAcquisitionMapper.update(orderPetAcquisition);

        }


    }

}
