package com.lxc.store.service.impl;

import com.lxc.store.entity.Address;
import com.lxc.store.mapper.AddressMapper;
import com.lxc.store.mapper.DistrictMapper;
import com.lxc.store.service.IAddressService;
import com.lxc.store.service.IDistrictService;
import com.lxc.store.service.ex.AddressCountLimitException;
import com.lxc.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xc
 * @date 2022/6/6 16:27
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    //在添加用户的收货地址的业务层依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private int maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer addressCount  = addressMapper.countByUid(uid);
        if(addressCount>=maxCount){
            throw new AddressCountLimitException("收货地址数量已经达到上限值"+maxCount+"条");
        }

        //补全address对象数据，省市区
        address.setProvinceName(districtService.getByCode(address.getProvinceCode()));
        address.setCityName(districtService.getByCode(address.getCityCode()));
        address.setAreaName(districtService.getByCode(address.getAreaCode()));
        address.setUid(uid);
        Integer isDefault = addressCount == 0? 1: 0;
        address.setIsDefault(isDefault);
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        //插入数据
        Integer rows = addressMapper.insert(address);
        if(rows!=1){
            throw new InsertException("新增收货地址失败");
        }

    }
}
