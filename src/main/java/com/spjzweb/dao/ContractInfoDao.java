package com.spjzweb.dao;

import com.spjzweb.entity.ContractInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ContractInfoDao {

    //分页模糊查询合同信息
    public List<HashMap<String,Object>> getAllByLike(@Param("contract_no") String contract_no,@Param("customer_spec") String customer_spec,@Param("od") String od,@Param("wt") String wt,@Param("threading_type") String threading_type, @Param("skip") int skip, @Param("take") int take);

    //模糊查询合同信息总数
    public int getCountAllByLike(@Param("contract_no") String contract_no,@Param("customer_spec") String customer_spec,@Param("od") String od,@Param("wt") String wt,@Param("threading_type") String threading_type);

    //修改合同信息
    public int updateContractInfo(ContractInfo contractInfo);

    //增加合同信息
    public int addContractInfo(ContractInfo contractInfo);

    //删除合同信息
    public int delContractInfo(String[] arrId);

    //添加合同的时候判定合同编号是否存在
    public List<ContractInfo> getContractInfoByContractNoOfAdd(@Param("contract_no") String contract_no);

    //修改合同信息的时候判定是否还有相同的合同编号
    public List<ContractInfo> getContractInfoByContractNoOfEdit(@Param("contract_no") String contract_no);

    //获取下拉框所需的所有的合同编号(客户端实验)
    public List<ContractInfo>getAllDropDownContractNoOfWinform();

    //根据合同编号查询合同信息
    public ContractInfo getContractInfoByNo(@Param("contract_no") String contract_no);
}
