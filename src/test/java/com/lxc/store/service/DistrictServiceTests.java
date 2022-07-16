package com.lxc.store.service;

import com.lxc.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xc
 * @date 2022/6/7 14:52
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void testGetByParent(){
        List<District> list = districtService.findByParent("110100");
        for(District district:list){
            System.out.println(district.toString());
        }
    }

}
