package com.paladin.framework.common;

import io.swagger.annotations.ApiModelProperty;

public class QuerySort {

    @ApiModelProperty("排序字段")
    private String sort;
    @ApiModelProperty("排序方式")
    private String order;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
