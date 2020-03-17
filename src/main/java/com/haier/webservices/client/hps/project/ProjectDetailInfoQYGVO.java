
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectDetailInfoQYGVO", propOrder = {
    "listCount",
    "projectDetailInfoVOList"
})
public class ProjectDetailInfoQYGVO
    extends ToString
{

    protected Integer listCount;
    @XmlElement(nillable = true)
    protected List<ProjectDetailInfoVO> projectDetailInfoVOList;

     
    public Integer getListCount() {
        return listCount;
    }

     
    public void setListCount(Integer value) {
        this.listCount = value;
    }

     
    public List<ProjectDetailInfoVO> getProjectDetailInfoVOList() {
        if (projectDetailInfoVOList == null) {
            projectDetailInfoVOList = new ArrayList<ProjectDetailInfoVO>();
        }
        return this.projectDetailInfoVOList;
    }

}
