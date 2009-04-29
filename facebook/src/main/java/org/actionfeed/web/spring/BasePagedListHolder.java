package org.actionfeed.web.spring;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;

import java.util.List;

/**
 * 
 */
public class BasePagedListHolder extends PagedListHolder {

    public BasePagedListHolder() {
    }

    public BasePagedListHolder(List source) {
        super(source);
    }

    public BasePagedListHolder(List source, SortDefinition sort) {
        super(source, sort);
    }

    /**
     * Protecting against negative page numbers coming in.
     * @param page The page to display
     */
    public void setPage(int page) {
        if(page > 0)
            super.setPage(page);
    }

    public boolean isPopulated() {
        return null != getSource() && getSource().size() > 0;
    }

    
}
