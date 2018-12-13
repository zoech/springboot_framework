package com.imeee.skeletons.facade.basemodel;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:32
 */

@Data
public class PageData {
    Long total; // 总结果数
    int count; // 当前页结果数

    int page; // 请求的页码
    int size; // 请求的大小

    String description; // 数据说明

    List data;

    public PageData(){

    }

    public PageData(int page, int size, List data, long total){
        this.total = total;
        this.page = page;
        this.size = size;
        this.count = data.size();

        this.data = data;
    }
}
