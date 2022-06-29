package com.europa.accounting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.Member;
import com.europa.accounting.param.AddMemberParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListMemberParam;
import com.europa.accounting.vo.ListMemberVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengwen
 * @since 2022-06-23
 */
public interface IMemberService extends IService<Member> {

    void add(AddMemberParam param);

    void edit(AddMemberParam param);

    Result<Page<ListMemberVO>> list(ListMemberParam param);

    void del(IdParam param);

    ListMemberVO get(Integer id);
}
