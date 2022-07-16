package com.lxc.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xc
 * @date 2022/5/23 16:30
 */

/** 用户的实体类：SpringBoot约定大于配置    */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

}
