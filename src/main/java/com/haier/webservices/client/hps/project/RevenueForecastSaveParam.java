
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "revenueForecastSaveParam", propOrder = {
    "createdById",
    "forecastYear",
    "id",
    "lastModifiedById",
    "month1",
    "month10",
    "month11",
    "month12",
    "month2",
    "month3",
    "month4",
    "month5",
    "month6",
    "month7",
    "month8",
    "month9",
    "projectId",
    "type"
})
public class RevenueForecastSaveParam
    extends ToString
{

    protected String createdById;
    protected String forecastYear;
    protected String id;
    protected String lastModifiedById;
    protected Double month1;
    protected Double month10;
    protected Double month11;
    protected Double month12;
    protected Double month2;
    protected Double month3;
    protected Double month4;
    protected Double month5;
    protected Double month6;
    protected Double month7;
    protected Double month8;
    protected Double month9;
    protected String projectId;
    protected String type;

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getForecastYear() {
        return forecastYear;
    }

     
    public void setForecastYear(String value) {
        this.forecastYear = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public Double getMonth1() {
        return month1;
    }

     
    public void setMonth1(Double value) {
        this.month1 = value;
    }

     
    public Double getMonth10() {
        return month10;
    }

     
    public void setMonth10(Double value) {
        this.month10 = value;
    }

     
    public Double getMonth11() {
        return month11;
    }

     
    public void setMonth11(Double value) {
        this.month11 = value;
    }

     
    public Double getMonth12() {
        return month12;
    }

     
    public void setMonth12(Double value) {
        this.month12 = value;
    }

     
    public Double getMonth2() {
        return month2;
    }

     
    public void setMonth2(Double value) {
        this.month2 = value;
    }

     
    public Double getMonth3() {
        return month3;
    }

     
    public void setMonth3(Double value) {
        this.month3 = value;
    }

     
    public Double getMonth4() {
        return month4;
    }

     
    public void setMonth4(Double value) {
        this.month4 = value;
    }

     
    public Double getMonth5() {
        return month5;
    }

     
    public void setMonth5(Double value) {
        this.month5 = value;
    }

     
    public Double getMonth6() {
        return month6;
    }

     
    public void setMonth6(Double value) {
        this.month6 = value;
    }

     
    public Double getMonth7() {
        return month7;
    }

     
    public void setMonth7(Double value) {
        this.month7 = value;
    }

     
    public Double getMonth8() {
        return month8;
    }

     
    public void setMonth8(Double value) {
        this.month8 = value;
    }

     
    public Double getMonth9() {
        return month9;
    }

     
    public void setMonth9(Double value) {
        this.month9 = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getType() {
        return type;
    }

     
    public void setType(String value) {
        this.type = value;
    }

}
