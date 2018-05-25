package com.spjzweb.dao;

import com.spjzweb.entity.ClientAuth;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ClientAuthDao {


    public List<ClientAuth> getAllByLike(@Param("encrypt_code") String encrypt_code,@Param("encrypt_code2") String encrypt_code2);


    public int updateClientAuth(ClientAuth clientAuth);
}
