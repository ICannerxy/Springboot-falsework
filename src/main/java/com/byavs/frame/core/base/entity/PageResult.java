package com.byavs.frame.core.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qibin.long on 2017/4/19.
 */
public class PageResult<T> implements Serializable {
    private long total;
    private List<T> list;
    private long startRow;
    private long endRow;
    private int pageNum;
    private int pageSize;
    private boolean needPaged = false;

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.needPaged && list != null ? list.subList((int) startRow, (int) endRow) : list;
    }

    public void setNeedPaged(int pageNum, int pageSize) {
        //默认每页10条
        this.pageSize = pageSize > 0 ? pageSize : 10;
        //默认第1页
        this.pageNum = pageNum > 0 ? pageNum : 1;
        this.startRow = (this.pageNum - 1) * this.pageSize;
        if (this.startRow >= this.total) {
            //取最大页码
            long maxPageNum = this.total / this.pageSize;
            if (maxPageNum > 0) {
                this.startRow = this.total % this.pageSize != 0 ? maxPageNum * this.pageSize : (maxPageNum - 1) * this.pageSize;
            } else {
                this.startRow = 0;
            }
        }
        this.endRow = this.startRow + this.pageSize;
        if (this.endRow >= this.total) {
            this.endRow = this.total;
        }
        this.needPaged = true;
    }
}