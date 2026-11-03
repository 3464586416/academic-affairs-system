package com.city.springboot_big_schoolwork.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private long total;
    private long page;
    private long size;
    private long pages;
    private List<T> records;

    public static <T> PageResult<T> of(IPage<T> page) {
        PageResult<T> pr = new PageResult<>();
        pr.setTotal(page.getTotal());
        pr.setPage(page.getCurrent());
        pr.setSize(page.getSize());
        pr.setPages(page.getPages());
        pr.setRecords(page.getRecords());
        return pr;
    }

    public static <T> PageResult<T> of(long total, long page, long size, List<T> records) {
        PageResult<T> pr = new PageResult<>();
        pr.setTotal(total);
        pr.setPage(page);
        pr.setSize(size);
        pr.setPages((total + size - 1) / size);
        pr.setRecords(records);
        return pr;
    }
}
