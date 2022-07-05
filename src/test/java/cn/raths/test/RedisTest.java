package cn.raths.test;

import cn.raths.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest extends BaseTest {

    @Autowired
        private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception{

        Object name = redisTemplate.opsForValue().get("c6673dff-47b3-4526-b003-842a6368b043");
        System.out.println(name);
    }

//    @Test
//    public void test2() throws Exception{
//        Shop shop = new Shop();
//        shop.setName("宜宾燃面");
//        shop.setId(222L);
//        redisTemplate.opsForValue().set("shop", shop);
//
//        Shop shop2 = (Shop)redisTemplate.opsForValue().get("shop");
//        System.out.println(shop2.getId());
//        System.out.println(shop2);
//    }
}
