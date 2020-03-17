
package com.haier.webservices.client.hps.verify;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageableVo", propOrder = {
    "list",
    "page",
    "rows",
    "totalCount"
})
public class PageableVo {

    @XmlElement(nillable = true)
    protected List<Object> list;
    protected int page;
    protected int rows;
    protected long totalCount;

     
    public List<Object> getList() {
        if (list == null) {
            list = new ArrayList<Object>();
        }
        return this.list;
    }

     
    public int getPage() {
        return page;
    }

     
    public void setPage(int value) {
        this.page = value;
    }

     
    public int getRows() {
        return rows;
    }

     
    public void setRows(int value) {
        this.rows = value;
    }

     
    public long getTotalCount() {
        return totalCount;
    }

     
    public void setTotalCount(long value) {
        this.totalCount = value;
    }

}
