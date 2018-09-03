package com.spjzweb.dao;

import com.spjzweb.entity.ClientAuth;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ClientAuthDao {

    //获取所有的客户端验证信息
    public List<ClientAuth> getAllByLike(@Param("encrypt_code") String encrypt_code,@Param("encrypt_code2") String encrypt_code2);

    //修改客户端验证信息
    public int updateClientAuth(ClientAuth clientAuth);

    //添加客户端验证信息
    public int addClientAuth(ClientAuth clientAuth);

    //根据id删除客户端验证信息
    public int delClientAuth(@Param("id") int id);

}
