package com.mes.bf.cmn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mes.bf.cmn.vo.FinProdCodeVO;

@Mapper
public interface FinProdMapper {
	List<FinProdCodeVO> listFinProd(String finName);
}
