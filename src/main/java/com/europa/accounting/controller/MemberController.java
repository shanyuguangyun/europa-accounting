package com.europa.accounting.controller;


import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.param.AddMemberParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListMemberParam;
import com.europa.accounting.service.IMemberService;
import com.europa.accounting.vo.ListMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fengwen
 * @date 2022/6/22
 * @description 成员管理
 **/
@Slf4j
@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @PostMapping("add")
    public Result<String> add(@RequestBody AddMemberParam param) {
        memberService.add(param);
        return Result.ok("新增成功");
    }

    @PostMapping("edit")
    public Result<String> edit(@RequestBody AddMemberParam param) {
        memberService.edit(param);
        return Result.ok("修改成功");
    }

    @GetMapping("get")
    public Result<ListMemberVO> get(@RequestParam Integer id) {
        return Result.ok(memberService.get(id));
    }

    @PostMapping("del")
    public Result<String> del(@RequestBody IdParam param) {
        memberService.del(param);
        return Result.ok("删除成功");
    }

    @PostMapping("list")
    public Result<Page<ListMemberVO>> list(@RequestBody ListMemberParam param) {
        return memberService.list(param);
    }

}
