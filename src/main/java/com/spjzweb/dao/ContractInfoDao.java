package com.spjzweb.dao;

import com.spjzweb.entity.ContractInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ContractInfoDao {

    //模糊搜索带分页
    public List<HashMap<String,Object>> getAllByLike(@Param("contract_no") String contract_no, @Param("skip") int skip, @Param("take") int take);

    //模糊搜索总数
    public int getCountAllByLike(@Param("contract_no") String contract_no);

    //修改ItemRecord
    public int updateContractInfo(ContractInfo contractInfo);
    //增加ItemRecord
    public int addContractInfo(ContractInfo contractInfo);
    //删除ItemRecord
    public int delContractInfo(String[] arrId);

    //根据 contract_no 得到 ContractInfo 集合
    public List<ContractInfo> getContractInfoByContractNo(@Param("contract_no") String contract_no);

}
