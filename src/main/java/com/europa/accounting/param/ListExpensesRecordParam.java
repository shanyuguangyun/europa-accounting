package com.europa.accounting.param;

import com.europa.accounting.common.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 查询消费记录参数
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ListExpensesRecordParam extends PageParam {

    private String name;

    private String address;

    @NotNull(message = "消费类型不能为空")
    private Integer type;
}
