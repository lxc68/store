package com.lxc.store.service;

import com.lxc.store.entity.District;

import java.util.List;

/**
 * @author xc
 * @date 2022/6/7 14:35
 */
public interface IDistrictService {

    List<District> findByParent(String parent);

    String getByCode(String code);
}
