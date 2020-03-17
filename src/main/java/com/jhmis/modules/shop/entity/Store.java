/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.shop.entity.dealer.Dealer;

/**
 * 店铺管理Entity
 *
 * @author tity
 * @version 2018-07-22
 */
public class Store extends DataEntity<Store> {

    private static final long serialVersionUID = 1L;
    private String dealerId;        // 供应商ID
    private String storeName;        // 店铺名称
    private Integer grade;        // 店铺等级
    private String state;        // 店铺状态
    private Date closeTime;        // 店铺关闭时间
    private String closeInfo;        // 店铺关闭原因
    private Date expiryTime;        // 到期时间
    private Double dynamicScore;        // 店铺动态评分
    private Integer applyYear;        // 申请时长
    private String isSelfSupport;        // 是否自营
    private Integer collectNum;        // 店铺收藏数量
    private String slidePicUrl;        // 店铺幻灯片链接
    private String slideLinkUrl;        // 店铺幻灯片链接
    private Double scoreProductQuality;        // 产品质量评分
    private Double scoreDemandResponse;        // 需求响应评分
    private Double scoreDeliveryCredit;        // 物流配送评分
    private Double scoreSupplySpeed;        // 供货速度评分
    private Double scoreCustomerServic;        // 售后服务评分
    private String sort;        // 店铺排序
    private String contractid;        // 消费者保障服务
    private Date beginCloseTime;        // 开始 店铺关闭时间
    private Date endCloseTime;        // 结束 店铺关闭时间
    private Date beginExpiryTime;        // 开始 到期时间
    private Date endExpiryTime;        // 结束 到期时间
    private Integer beginCollectNum;        // 开始 店铺收藏数量
    private Integer endCollectNum;        // 结束 店铺收藏数量
    private Dealer dealer; // 供应商信息
    private String isCollect;  //是否收藏

    private List<StoreGoods> storeGoodsList;

    public Store() {
        super();
    }

    public Store(String id) {
        super(id);
    }

    public List<StoreGoods> getStoreGoodsList() {
        return storeGoodsList;
    }

    public void setStoreGoodsList(List<StoreGoods> storeGoodsList) {
        this.storeGoodsList = storeGoodsList;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    @ExcelField(title = "供应商ID", align = 2, sort = 1)
    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    @ExcelField(title = "店铺名称", align = 2, sort = 2)
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @ExcelField(title = "店铺等级", align = 2, sort = 3)
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @ExcelField(title = "店铺状态", dictType = "enable", align = 2, sort = 4)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "店铺关闭时间", align = 2, sort = 5)
    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    @ExcelField(title = "店铺关闭原因", align = 2, sort = 6)
    public String getCloseInfo() {
        return closeInfo;
    }

    public void setCloseInfo(String closeInfo) {
        this.closeInfo = closeInfo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "到期时间", align = 2, sort = 7)
    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    @ExcelField(title = "店铺动态评分", align = 2, sort = 8)
    public Double getDynamicScore() {
        return dynamicScore;
    }

    public void setDynamicScore(Double dynamicScore) {
        this.dynamicScore = dynamicScore;
    }

    @ExcelField(title = "申请时长", align = 2, sort = 9)
    public Integer getApplyYear() {
        return applyYear;
    }

    public void setApplyYear(Integer applyYear) {
        this.applyYear = applyYear;
    }

    @ExcelField(title = "是否自营", dictType = "yes_no", align = 2, sort = 10)
    public String getIsSelfSupport() {
        return isSelfSupport;
    }

    public void setIsSelfSupport(String isSelfSupport) {
        this.isSelfSupport = isSelfSupport;
    }

    @ExcelField(title = "店铺收藏数量", align = 2, sort = 11)
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @ExcelField(title = "店铺幻灯片链接", align = 2, sort = 12)
    public String getSlidePicUrl() {
        return slidePicUrl;
    }

    public void setSlidePicUrl(String slidePicUrl) {
        this.slidePicUrl = slidePicUrl;
    }

    @ExcelField(title = "店铺幻灯片链接", align = 2, sort = 13)
    public String getSlideLinkUrl() {
        return slideLinkUrl;
    }

    public void setSlideLinkUrl(String slideLinkUrl) {
        this.slideLinkUrl = slideLinkUrl;
    }

    @ExcelField(title = "产品质量评分", align = 2, sort = 14)
    public Double getScoreProductQuality() {
        return scoreProductQuality;
    }

    public void setScoreProductQuality(Double scoreProductQuality) {
        this.scoreProductQuality = scoreProductQuality;
    }

    @ExcelField(title = "需求响应评分", align = 2, sort = 15)
    public Double getScoreDemandResponse() {
        return scoreDemandResponse;
    }

    public void setScoreDemandResponse(Double scoreDemandResponse) {
        this.scoreDemandResponse = scoreDemandResponse;
    }

    @ExcelField(title = "物流配送评分", align = 2, sort = 16)
    public Double getScoreDeliveryCredit() {
        return scoreDeliveryCredit;
    }

    public void setScoreDeliveryCredit(Double scoreDeliveryCredit) {
        this.scoreDeliveryCredit = scoreDeliveryCredit;
    }

    @ExcelField(title = "供货速度评分", align = 2, sort = 17)
    public Double getScoreSupplySpeed() {
        return scoreSupplySpeed;
    }

    public void setScoreSupplySpeed(Double scoreSupplySpeed) {
        this.scoreSupplySpeed = scoreSupplySpeed;
    }

    @ExcelField(title = "售后服务评分", align = 2, sort = 18)
    public Double getScoreCustomerServic() {
        return scoreCustomerServic;
    }

    public void setScoreCustomerServic(Double scoreCustomerServic) {
        this.scoreCustomerServic = scoreCustomerServic;
    }

    @ExcelField(title = "店铺排序", align = 2, sort = 24)
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @ExcelField(title = "消费者保障服务", align = 2, sort = 25)
    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid;
    }

    public Date getBeginCloseTime() {
        return beginCloseTime;
    }

    public void setBeginCloseTime(Date beginCloseTime) {
        this.beginCloseTime = beginCloseTime;
    }

    public Date getEndCloseTime() {
        return endCloseTime;
    }

    public void setEndCloseTime(Date endCloseTime) {
        this.endCloseTime = endCloseTime;
    }

    public Date getBeginExpiryTime() {
        return beginExpiryTime;
    }

    public void setBeginExpiryTime(Date beginExpiryTime) {
        this.beginExpiryTime = beginExpiryTime;
    }

    public Date getEndExpiryTime() {
        return endExpiryTime;
    }

    public void setEndExpiryTime(Date endExpiryTime) {
        this.endExpiryTime = endExpiryTime;
    }

    public Integer getBeginCollectNum() {
        return beginCollectNum;
    }

    public void setBeginCollectNum(Integer beginCollectNum) {
        this.beginCollectNum = beginCollectNum;
    }

    public Integer getEndCollectNum() {
        return endCollectNum;
    }

    public void setEndCollectNum(Integer endCollectNum) {
        this.endCollectNum = endCollectNum;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

}