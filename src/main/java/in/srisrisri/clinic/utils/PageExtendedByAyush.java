/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author akr2
 */
public class PageExtendedByAyush implements Page{

    
    List content;
    Pageable pageable;
    Sort sort;
    
    
    
    public PageExtendedByAyush(List list,Pageable pageable) {
       this.content=list;
        this.pageable=pageable;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

 
    
    
    @Override
    public int getTotalPages() {
   return 1;
    }

    @Override
    public long getTotalElements() {
      return 1;
    }

    @Override
    public Page map(Function converter) {
        return null;
    }

    @Override
    public int getNumber() {
      return 1;
    }

    @Override
    public int getSize() {
     return 1;
    }

    @Override
    public int getNumberOfElements() {
      return 1;
    }

    @Override
    public List getContent() {
     return content;
    }

    @Override
    public boolean hasContent() {
    return false;
    }

    @Override
    public Sort getSort() {
    return sort;
    }

    @Override
    public boolean isFirst() {
  return false;
            }

    @Override
    public boolean isLast() {
    return false;
    }

    @Override
    public boolean hasNext() {
   return false;
    }

    @Override
    public boolean hasPrevious() {
    return false;
    }

    @Override
    public Pageable nextPageable() {
    return  getPageable();
    }

    @Override
    public Pageable previousPageable() {
    return  getPageable();
    }

    @Override
    public Iterator iterator() {
    return null;
    }
    
}
