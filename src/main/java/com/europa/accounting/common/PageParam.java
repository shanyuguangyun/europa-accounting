package com.europa.accounting.common;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 分页参数
 **/
@Data
public class PageParam {

    private Integer pageNum;

    private Integer pageSize;

    private Integer offset;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
