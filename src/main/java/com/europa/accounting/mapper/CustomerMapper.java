package com.europa.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.europa.accounting.entity.Customer;
import com.europa.accounting.param.ListSendManParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengwen
 * @since 2022-06-23
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    int count(@Param("param") ListSendManParam param);

    List<Customer> list(@Param("param") ListSendManParam param);

    int del(@Param("id") Integer id);
}
