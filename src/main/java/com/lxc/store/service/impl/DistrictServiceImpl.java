package com.lxc.store.service.impl;

import com.lxc.store.entity.District;
import com.lxc.store.mapper.DistrictMapper;
import com.lxc.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xc
 * @date 2022/6/7 14:40
 */
@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {

        //为了网络传输效率，可以将无效数据设置为null
        List<District> list = districtMapper.findByParent(parent);
        for(District district:list){
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
