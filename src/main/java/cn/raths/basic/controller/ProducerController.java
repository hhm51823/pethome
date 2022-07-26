package cn.raths.basic.controller;

import cn.raths.basic.service.impl.RocketMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProducerController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @RequestMapping("/send")
    @ResponseBody
    public SendResult send(String msg)  {
        //formats: `topicName:tags`
        System.out.println(msg);
        return rocketMQProducer.sendMsg(msg);
    }

}