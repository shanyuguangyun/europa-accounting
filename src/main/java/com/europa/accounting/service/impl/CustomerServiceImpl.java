package com.europa.accounting.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.Customer;
import com.europa.accounting.exception.ApiException;
import com.europa.accounting.mapper.CustomerMapper;
import com.europa.accounting.param.AddSendManParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListSendManParam;
import com.europa.accounting.param.RegionParam;
import com.europa.accounting.service.ICustomerService;
import com.europa.accounting.service.RegionCodeService;
import com.europa.accounting.utils.ValidatorUtil;
import com.europa.accounting.vo.SendManListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RegionCodeService regionCodeService;

    @Override
    public void add(AddSendManParam param) {
        Customer customer = AddSendManParam.toCustomer(param);
        String regionJson = param.getRegionJson();
        RegionParam regionParam = JSONUtil.toBean(regionJson, RegionParam.class);
        ValidatorUtil.validateAndThrowException(regionParam, Default.class);
        appendRegion(regionParam, customer);

        this.save(customer);
    }

    private void appendRegion(RegionParam regionParam, Customer customer) {
        customer.setProvince(regionParam.getProvince().getValue());
        customer.setCity(regionParam.getCity().getValue());
        customer.setArea(regionParam.getArea().getValue());
        customer.setTown(regionParam.getTown().getValue());
        customer.setRegionCode(buildRegionCode(regionParam));
    }

    private String buildRegionCode(RegionParam regionParam) {
        return regionCodeService.build(regionParam);
    }

    @Override
    public void edit(AddSendManParam param) {
        if (param.getId() == null) {
            throw new ApiException("id不能为空");
        }
        Customer customer = AddSendManParam.toCustomer(param);
        String regionJson = param.getRegionJson();
        RegionParam regionParam = JSONUtil.toBean(regionJson, RegionParam.class);
        ValidatorUtil.validateAndThrowException(regionParam, Default.class);
        appendRegion(regionParam, customer);
        this.updateById(customer);
    }

    @Override
    public Result<Page<SendManListVO>> list(ListSendManParam param) {
        int count = customerMapper.count(param);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<Customer> list = customerMapper.list(param);
        List<SendManListVO> voList = list.stream()
                .map(customer -> {
                    SendManListVO vo = BeanUtil.copyProperties(customer, SendManListVO.class, "regionCode");
                    String regionCodeJson = customer.getRegionCode();
                    SendManListVO.RegionCode regionCode = JSONUtil.toBean(regionCodeJson, SendManListVO.RegionCode.class);
                    vo.setRegionCode(regionCode);
                    return vo;
                }).collect(toList());
        return Result.ok(Page.of(count, voList));
    }

    @Override
    public void del(IdParam param) {
        Integer id = param.getId();
        if(id == null) {
            throw new ApiException("id不能为空");
        }
        customerMapper.del(id);
    }
}
