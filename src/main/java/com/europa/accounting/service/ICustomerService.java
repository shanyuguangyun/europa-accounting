package com.europa.accounting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.Customer;
import com.europa.accounting.param.AddSendManParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListSendManParam;
import com.europa.accounting.vo.SendManListVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengwen
 * @since 2022-06-23
 */
public interface ICustomerService extends IService<Customer> {

    void add(AddSendManParam param);

    Result<Page<SendManListVO>> list(ListSendManParam param);

    void del(IdParam param);

    void edit(AddSendManParam param);
}
