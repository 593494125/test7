package com.springboot.configurer;//package com.springboot.configurer;
//
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RocketMQMessageListener(topic = "test_topic",consumerGroup = "my-consumer-group")
//public class MqListener implements RocketMQListener<MessageExt> {
//
//    @Override
//    public void onMessage(MessageExt messageExt) {
//        //获取消息内容
//        System.out.println("消息正在被消费========="+messageExt.getMsgId()+"body==="+new String(messageExt.getBody()));
//        System.out.println("keys==="+new String(messageExt.getKeys()));
//        System.out.println("tags==="+new String(messageExt.getTags()));
//        System.out.println("topic==="+new String(messageExt.getTopic()));
//        //修改的时候用锁机制来，防止并发性多次修改
//
//    }
//}
