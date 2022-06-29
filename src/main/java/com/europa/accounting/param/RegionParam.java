package com.europa.accounting.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author fengwen
 * @date 2022/6/23
 * @description TODO
 **/
@Data
public class RegionParam {

    @NotNull(message = "省份不能为空")
    private RegionItem province;

    @NotNull(message = "市区不能为空")
    private RegionItem city;

    @NotNull(message = "县不能为空")
    private RegionItem area;

    @NotNull(message = "街道不能为空")
    private RegionItem town;

    @Data
    public static class RegionItem {

        private String key;

        private String value;
    }
}
