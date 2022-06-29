package cn.raths.test;

import cn.raths.base.BaseTest;
import cn.raths.org.domain.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest extends BaseTest {

    @Autowired
        private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception{
       redisTemplate.opsForValue().set("name", "佳佳");

        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void test2() throws Exception{
        Shop shop = new Shop();
        shop.setName("宜宾燃面");
        shop.setId(222L);
        redisTemplate.opsForValue().set("shop", shop);

        Shop shop2 = (Shop)redisTemplate.opsForValue().get("shop");
        System.out.println(shop2.getId());
        System.out.println(shop2);
    }
}
