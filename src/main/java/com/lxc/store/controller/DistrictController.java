package com.lxc.store.controller;

import com.lxc.store.entity.District;
import com.lxc.store.service.IDistrictService;
import com.lxc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xc
 * @date 2022/6/7 14:56
 */
@RestController
@RequestMapping("districts")
public class DistrictController {

    @Autowired
    private IDistrictService districtService;

    @GetMapping({"","/"})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> list = districtService.findByParent(parent);
        return new JsonResult<>(200,list);
    }
}
