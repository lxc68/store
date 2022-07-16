package com.lxc.store.mapper;

import com.lxc.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xc
 * @date 2022/6/7 14:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void getDistrictByParent(){
        List<District> list = districtMapper.findByParent("110100");
        for(District d :list){
            System.out.println(d.toString());
        }
    }
    @Test
    public void getDistrictByCode(){
        System.out.println(districtMapper.findNameByCode("610000"));
    }
}
