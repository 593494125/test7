package com.springboot.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pages<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 页编号 : 第几页
     */
    private int pageNo = 1;
    /**
     * 页大小 : 每页的数量
     */
    private int pageSize = 20;

    /**
     * 偏移量 : 第一条数据在表中的位置
     */
    private int offset;

    /**
     * 限定数 : 每页的数量
     */
    private int limit;

    // --结果 --//
    /**
     * 查询结果
     */
    private List<T> rows = new ArrayList<T>();

    /**
     * 总条数
     */
    private int total;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 计算偏移量
     */
    private void calcOffset() {
        this.offset = ((pageNo - 1) * pageSize);
    }

    /**
     * 计算限定数
     */
    private void calcLimit() {
        this.limit = pageSize;
    }

    public Pages() {
        this.calcOffset();
        this.calcLimit();
    }

    public Pages(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.calcOffset();
        this.calcLimit();
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * 取得页内的记录列表.
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setRows(final List<T> rows) {
        this.rows = rows;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     */
    public void setTotal(final int total) {
        this.total = total;
        this.totalPages = this.getTotalPages();
    }

    /**
     * 根据pageSize与total计算总页数, 默认值为-1.
     */
//    public int getTotalPages() {
//        if (total < 0) {
//            return -1;
//        }
//        int pages = total / pageSize;
//        return total % pageSize > 0 ? ++pages : pages;
//    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
