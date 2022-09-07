package com.europa.accounting.controller;


import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.param.AddExpensesRecordParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListExpensesRecordParam;
import com.europa.accounting.service.IExpensesRecordService;
import com.europa.accounting.vo.ListExpensesRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author fengwen
 * @date 2022/6/22
 * @description TODO
 **/
@Slf4j
@RestController
@RequestMapping("expenses")
public class ExpensesRecordController {

    @Autowired
    private IExpensesRecordService expensesRecordService;

    @PostMapping("add")
    public Result<String> add(@RequestBody @Valid AddExpensesRecordParam param) {
        expensesRecordService.add(param);
        return Result.ok("新增成功");
    }

    @PostMapping("edit")
    public Result<String> edit(@RequestBody @Valid AddExpensesRecordParam param) {
        expensesRecordService.edit(param);
        return Result.ok("修改成功");
    }

    @PostMapping("del")
    public Result<String> del(@RequestBody IdParam param) {
        expensesRecordService.del(param);
        return Result.ok("删除成功");
    }

    @PostMapping("list")
    public Result<Page<ListExpensesRecordVO>> list(@RequestBody @Valid ListExpensesRecordParam param) {
        return expensesRecordService.list(param);
    }

}
