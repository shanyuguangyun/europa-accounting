package com.europa.accounting.service;

import cn.hutool.json.JSONUtil;
import com.europa.accounting.param.RegionParam;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author fengwen
 * @date 2022/6/24
 * @description TODO
 **/
@Service
public class RegionCodeService {

    public String build(RegionParam regionParam) {
        RegionCode regionCode = new RegionCode();
        regionCode.setProvince(regionParam.getProvince().getKey());
        regionCode.setCity(regionParam.getCity().getKey());
        regionCode.setArea(regionParam.getArea().getKey());
        regionCode.setTown(regionParam.getTown().getKey());
        return JSONUtil.toJsonStr(regionCode);
    }

    /**
     * 前端需要回显行政区域的数据格式
     * {
     *   province: '350000',
     *   city: '350100',
     *   area: '350104',
     *   town: '350104008'
     * }
     */
    @Data
    public static class RegionCode {

        private String province;

        private String city;

        private String area;

        private String town;
    }
}
