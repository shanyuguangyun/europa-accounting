package com.europa.accounting.common;

import lombok.Data;

import java.util.List;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description 分页结果
 **/
@Data
public class Page<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private List<T> data;

    public static <T> Page<T> empty() {
        Page<T> page = new Page<>();
        page.setTotal(0);
        page.setData(null);
        return page;
    }

    public static<T> Page<T> of(int count, List<T> list) {
        Page<T> page = new Page<>();
        page.setTotal(count);
        page.setData(list);
        return page;
    }
}
