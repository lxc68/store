package com.lxc.store.mapper;

import com.lxc.store.entity.District;

import java.util.List;

/**
 * 获取全国所有省、市、区
 * @author xc
 * @date 2022/6/7 14:01
 */
public interface DistrictMapper {
    List<District> findByParent(String parent);

    /**
     * 根据code查询当前省市区的名称
     * @param code
     * @return
     */
    String findNameByCode(String code);
}
