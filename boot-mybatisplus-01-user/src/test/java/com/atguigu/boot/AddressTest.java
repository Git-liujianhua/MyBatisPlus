package com.atguigu.boot;

import com.atguigu.boot.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressTest {

    @Test
    void insertTest(){
        Address address = new Address();
        address.setCity("温州");
        address.setStreet("阳光大道");
        address.setZipcode("0102");
        boolean insert = address.insert();
        System.out.println(insert);
    }
}
