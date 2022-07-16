package com.lxc.store.mapper;

import com.lxc.store.entity.Address;

/**
 * @author xc
 * @date 2022/6/6 15:19
 */
public interface AddressMapper {

    /**
     * 新增收货地址
     * @param address
     * @return
     */
    Integer insert(Address address);

    /**
     * 统计某用户的收货地址数据的数量
     * @param uid
     * @return
     */
    Integer countByUid(Integer uid);

}
