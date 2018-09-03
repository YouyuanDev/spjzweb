package com.spjzweb.dao;

import com.spjzweb.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface RoleDao {

    //根据角色编号和名称获取所有角色信息
    public List<HashMap<String,Object>>getAllRoleByLike(@Param("role_no") String role_no, @Param("role_name") String role_name);

    //分页获取角色列表
    public List<HashMap<String,Object>> getAllByLike(@Param("role_no") String role_no, @Param("role_name") String role_name, @Param("skip") int skip, @Param("take") int take);

    //分页获取角色列表总数
    public int getCountAllByLike(@Param("role_no") String role_no, @Param("role_name") String role_name);

    //修改角色信息
    public int updateRole(Role role);

    //增加角色信息
    public int addRole(Role role);

    //删除角色信息
    public int delRole(String[] arrId);

    //根据角色编号得到角色信息
    public List<Role> getRoleByRoleNo(@Param("role_no") String role_no);
}
