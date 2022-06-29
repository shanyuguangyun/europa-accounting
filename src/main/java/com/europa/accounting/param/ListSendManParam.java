package com.europa.accounting.param;

import com.europa.accounting.common.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 查询寄件人列表
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ListSendManParam extends PageParam {

    private String company;

    private String name;

    private String mobile;

    private String address;

}
