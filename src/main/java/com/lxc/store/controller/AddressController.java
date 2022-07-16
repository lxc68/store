package com.lxc.store.controller;

import com.lxc.store.entity.Address;
import com.lxc.store.service.IAddressService;
import com.lxc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author xc
 * @date 2022/6/6 20:13
 */
@RestController
@RequestMapping("addresses")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        //从session中获取用户id和名字
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<Void>(200);
    }
}
