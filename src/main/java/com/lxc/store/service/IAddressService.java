package com.lxc.store.service;

import com.lxc.store.entity.Address;

/**
 * 处理收货地址的业务层接口
 * @author xc
 * @date 2022/6/6 16:19
 */
public interface IAddressService {

    void addNewAddress(Integer uid, String username, Address address);
}
