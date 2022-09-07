package com.europa.accounting.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.europa.accounting.common.Page;
import com.europa.accounting.common.Result;
import com.europa.accounting.entity.ExpensesRecord;
import com.europa.accounting.exception.ApiException;
import com.europa.accounting.mapper.ExpensesRecordMapper;
import com.europa.accounting.param.AddExpensesRecordParam;
import com.europa.accounting.param.IdParam;
import com.europa.accounting.param.ListExpensesRecordParam;
import com.europa.accounting.param.RegionParam;
import com.europa.accounting.service.IExpensesRecordService;
import com.europa.accounting.service.RegionCodeService;
import com.europa.accounting.utils.ValidatorUtil;
import com.europa.accounting.vo.ListExpensesRecordVO;
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
public class ExpensesRecordServiceImpl extends ServiceImpl<ExpensesRecordMapper, ExpensesRecord> implements IExpensesRecordService {

    @Autowired
    private ExpensesRecordMapper expensesRecordMapper;

    @Autowired
    private RegionCodeService regionCodeService;

    @Override
    public void add(AddExpensesRecordParam param) {
        ExpensesRecord record = AddExpensesRecordParam.toRecord(param);
        String regionJson = param.getRegionJson();
        RegionParam regionParam = JSONUtil.toBean(regionJson, RegionParam.class);
        ValidatorUtil.validateAndThrowException(regionParam, Default.class);
        appendRegion(regionParam, record);

        this.save(record);
    }

    private void appendRegion(RegionParam regionParam, ExpensesRecord record) {
        record.setProvince(regionParam.getProvince().getValue());
        record.setCity(regionParam.getCity().getValue());
        record.setArea(regionParam.getArea().getValue());
        record.setTown(regionParam.getTown().getValue());
        record.setRegionCode(buildRegionCode(regionParam));
    }

    private String buildRegionCode(RegionParam regionParam) {
        return regionCodeService.build(regionParam);
    }

    @Override
    public void edit(AddExpensesRecordParam param) {
        if (param.getId() == null) {
            throw new ApiException("id不能为空");
        }
        ExpensesRecord record = AddExpensesRecordParam.toRecord(param);
        String regionJson = param.getRegionJson();
        RegionParam regionParam = JSONUtil.toBean(regionJson, RegionParam.class);
        ValidatorUtil.validateAndThrowException(regionParam, Default.class);
        appendRegion(regionParam, record);
        this.updateById(record);
    }

    @Override
    public Result<Page<ListExpensesRecordVO>> list(ListExpensesRecordParam param) {
        int count = expensesRecordMapper.count(param);
        if (count == 0) {
            return Result.ok(Page.empty());
        }
        List<ExpensesRecord> list = expensesRecordMapper.list(param);
        List<ListExpensesRecordVO> voList = list.stream()
                .map(record -> {
                    ListExpensesRecordVO vo = BeanUtil.copyProperties(record, ListExpensesRecordVO.class, "regionCode");
                    String regionCodeJson = record.getRegionCode();
                    ListExpensesRecordVO.RegionCode regionCode = JSONUtil.toBean(regionCodeJson, ListExpensesRecordVO.RegionCode.class);
                    vo.setRegionCode(regionCode);
                    return vo;
                }).collect(toList());
        return Result.ok(Page.of(count, voList));
    }

    @Override
    public void del(IdParam param) {
        Integer id = param.getId();
        if (id == null) {
            throw new ApiException("id不能为空");
        }
        expensesRecordMapper.del(id);
    }
}
