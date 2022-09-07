package com.europa.accounting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.ExpensesRecord;
import com.europa.accounting.param.AddExpensesRecordParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListExpensesRecordParam;
import com.europa.accounting.vo.ListExpensesRecordVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengwen
 * @since 2022-06-23
 */
public interface IExpensesRecordService extends IService<ExpensesRecord> {

    void add(AddExpensesRecordParam param);

    Result<Page<ListExpensesRecordVO>> list(ListExpensesRecordParam param);

    void del(IdParam param);

    void edit(AddExpensesRecordParam param);
}
