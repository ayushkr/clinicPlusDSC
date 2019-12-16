/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.utils;


import org.springframework.data.domain.Page;



/**
 * A page is a sublist of a list of objects. It allows gain information about the position of it in the containing
 * entire list.
 * 
 * @param <T>
 * @author Oliver Gierke
 */
public final class PageCover<T>  {
    String sortColumn;
    String sortOrder;
    String filterColumn;
        String filter;
    String module;
    Page<T> pageList;

    public PageCover(Page<T> pageList) {
        setPageList(pageList);
    }

    public String getFilterColumn() {
        return filterColumn;
    }

    public void setFilterColumn(String filterColumn) {
        this.filterColumn = filterColumn;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Page<T> getPageList() {
        return pageList;
    }

    public void setPageList(Page<T> pageList) {
        this.pageList = pageList;
    }
    

	
}
