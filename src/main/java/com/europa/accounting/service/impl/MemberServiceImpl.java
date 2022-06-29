package com.europa.accounting.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.Member;
import com.europa.accounting.exception.ApiException;
import com.europa.accounting.mapper.MemberMapper;
import com.europa.accounting.param.AddMemberParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListMemberParam;
import com.europa.accounting.service.IMemberService;
import com.europa.accounting.vo.ListMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengwen
 * @since 2022-06-23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void add(AddMemberParam param) {
        Member member = AddMemberParam.toCustomer(param);
        this.save(member);
    }

    @Override
    public void edit(AddMemberParam param) {
        if (param.getId() == null) {
            throw new ApiException("id不能为空");
        }
        Member member = AddMemberParam.toCustomer(param);
        this.updateById(member);
    }

    @Override
    public Result<Page<ListMemberVO>> list(ListMemberParam param) {
        int count = memberMapper.count(param);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<Member> list = memberMapper.list(param);
        List<ListMemberVO> voList = list.stream()
                .map(item -> BeanUtil.copyProperties(item, ListMemberVO.class))
                .collect(toList());
        return Result.ok(Page.of(count, voList));
    }

    @Override
    public void del(IdParam param) {
        Integer id = param.getId();
        if (id == null) {
            throw new ApiException("id不能为空");
        }
        memberMapper.del(id);
    }

    @Override
    public ListMemberVO get(Integer id) {
        if (id == null) {
            throw new ApiException("id不能为空");
        }
        Member member = memberMapper.selectById(id);
        return BeanUtil.copyProperties(member, ListMemberVO.class);
    }
}
