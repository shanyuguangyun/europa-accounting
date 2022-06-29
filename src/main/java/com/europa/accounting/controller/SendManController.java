package com.europa.accounting.controller;


import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.param.AddSendManParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListSendManParam;
import com.europa.accounting.service.ICustomerService;
import com.europa.accounting.vo.SendManListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengwen
 * @date 2022/6/22
 * @description TODO
 **/
@Slf4j
@RestController
@RequestMapping("sendMan")
public class SendManController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("add")
    public Result<String> add(@RequestBody AddSendManParam param) {
        customerService.add(param);
        return Result.ok("新增成功");
    }

    @PostMapping("edit")
    public Result<String> edit(@RequestBody AddSendManParam param) {
        customerService.edit(param);
        return Result.ok("修改成功");
    }

    @PostMapping("del")
    public Result<String> del(@RequestBody IdParam param) {
        customerService.del(param);
        return Result.ok("删除成功");
    }

    @PostMapping("list")
    public Result<Page<SendManListVO>> list(@RequestBody ListSendManParam param) {
        return customerService.list(param);
    }

}
