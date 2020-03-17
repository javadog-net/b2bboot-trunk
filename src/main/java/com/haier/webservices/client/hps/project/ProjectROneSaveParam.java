
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectROneSaveParam", propOrder = {
    "addressCity",
    "addressCounty",
    "addressDetail",
    "addressProvince",
    "appealFileId",
    "appealFileUrl",
    "area",
    "authStatus",
    "authTime",
    "beAfterPurchase",
    "beAllopatric",
    "beGroupBuy",
    "beImgageProject",
    "beManualSelectProjectFinish",
    "beMeetFirstParty",
    "beSendGpms",
    "beStrategy",
    "beTurnStraightGuest",
    "bigAndSmall",
    "brandInfoCode",
    "brandInfoName",
    "cancelPersonCode",
    "cancelReason",
    "cancelTime",
    "center",
    "cleanEnergy",
    "cleanEnergyType",
    "createdById",
    "dataSource",
    "districtCode",
    "domainModel",
    "domainType",
    "estimatedTimeBid",
    "estimatedWinningTime",
    "eucId",
    "firstCompanyId",
    "firstCompanyName",
    "firstCompanyOrgCode",
    "frequencyConversion",
    "funnelStage",
    "funnelStageUpdateTime",
    "gridCenterCode",
    "gridCenterName",
    "gridCode",
    "gridName",
    "gridUserCode",
    "gridUserName",
    "groupId",
    "id",
    "industryHomeCategory",
    "isEditing",
    "judgmentSheetStatus",
    "lastModifiedById",
    "lesFlag",
    "lesMsg",
    "loginLevel",
    "msgId",
    "productSeriesOneCode",
    "productSeriesOneName",
    "productSeriesTwoCode",
    "productSeriesTwoName",
    "profitSharing",
    "projectCode",
    "projectCreaterCode",
    "projectCreaterId",
    "projectCreaterName",
    "projectFinishDate",
    "projectLastModifiedDate",
    "projectManagerCenter",
    "projectManagerCode",
    "projectManagerId",
    "projectManagerName",
    "projectName",
    "projectSource",
    "projectStatus",
    "projectType",
    "purchaseForecastList",
    "salesModel",
    "samplePlateType",
    "sendGpmsResult",
    "sendGpmsTime",
    "skMaglevList",
    "strategyId"
})
public class ProjectROneSaveParam
    extends ToString
{

    protected String addressCity;
    protected String addressCounty;
    protected String addressDetail;
    protected String addressProvince;
    protected String appealFileId;
    protected String appealFileUrl;
    protected String area;
    protected String authStatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar authTime;
    protected Boolean beAfterPurchase;
    protected Boolean beAllopatric;
    protected Boolean beGroupBuy;
    protected Boolean beImgageProject;
    protected Boolean beManualSelectProjectFinish;
    protected Boolean beMeetFirstParty;
    protected String beSendGpms;
    protected Boolean beStrategy;
    protected Boolean beTurnStraightGuest;
    protected String bigAndSmall;
    protected String brandInfoCode;
    protected String brandInfoName;
    protected String cancelPersonCode;
    protected String cancelReason;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cancelTime;
    protected String center;
    protected String cleanEnergy;
    protected String cleanEnergyType;
    protected String createdById;
    protected String dataSource;
    protected String districtCode;
    protected String domainModel;
    protected String domainType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedTimeBid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedWinningTime;
    protected String eucId;
    protected String firstCompanyId;
    protected String firstCompanyName;
    protected String firstCompanyOrgCode;
    protected Boolean frequencyConversion;
    protected String funnelStage;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar funnelStageUpdateTime;
    protected String gridCenterCode;
    protected String gridCenterName;
    protected String gridCode;
    protected String gridName;
    protected String gridUserCode;
    protected String gridUserName;
    protected String groupId;
    protected String id;
    protected String industryHomeCategory;
    protected Boolean isEditing;
    protected Boolean judgmentSheetStatus;
    protected String lastModifiedById;
    protected String lesFlag;
    protected String lesMsg;
    protected Integer loginLevel;
    protected String msgId;
    protected String productSeriesOneCode;
    protected String productSeriesOneName;
    protected String productSeriesTwoCode;
    protected String productSeriesTwoName;
    protected String profitSharing;
    protected String projectCode;
    protected String projectCreaterCode;
    protected String projectCreaterId;
    protected String projectCreaterName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar projectFinishDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar projectLastModifiedDate;
    protected String projectManagerCenter;
    protected String projectManagerCode;
    protected String projectManagerId;
    protected String projectManagerName;
    protected String projectName;
    protected String projectSource;
    protected String projectStatus;
    protected String projectType;
    @XmlElement(nillable = true)
    protected List<ProjectPurchaseForecastSaveParam> purchaseForecastList;
    protected String salesModel;
    protected String samplePlateType;
    protected String sendGpmsResult;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendGpmsTime;
    @XmlElement(nillable = true)
    protected List<ProjectSKMaglevSaveParam> skMaglevList;
    protected String strategyId;

     
    public String getAddressCity() {
        return addressCity;
    }

     
    public void setAddressCity(String value) {
        this.addressCity = value;
    }

     
    public String getAddressCounty() {
        return addressCounty;
    }

     
    public void setAddressCounty(String value) {
        this.addressCounty = value;
    }

     
    public String getAddressDetail() {
        return addressDetail;
    }

     
    public void setAddressDetail(String value) {
        this.addressDetail = value;
    }

     
    public String getAddressProvince() {
        return addressProvince;
    }

     
    public void setAddressProvince(String value) {
        this.addressProvince = value;
    }

     
    public String getAppealFileId() {
        return appealFileId;
    }

     
    public void setAppealFileId(String value) {
        this.appealFileId = value;
    }

     
    public String getAppealFileUrl() {
        return appealFileUrl;
    }

     
    public void setAppealFileUrl(String value) {
        this.appealFileUrl = value;
    }

     
    public String getArea() {
        return area;
    }

     
    public void setArea(String value) {
        this.area = value;
    }

     
    public String getAuthStatus() {
        return authStatus;
    }

     
    public void setAuthStatus(String value) {
        this.authStatus = value;
    }

     
    public XMLGregorianCalendar getAuthTime() {
        return authTime;
    }

     
    public void setAuthTime(XMLGregorianCalendar value) {
        this.authTime = value;
    }

     
    public Boolean isBeAfterPurchase() {
        return beAfterPurchase;
    }

     
    public void setBeAfterPurchase(Boolean value) {
        this.beAfterPurchase = value;
    }

     
    public Boolean isBeAllopatric() {
        return beAllopatric;
    }

     
    public void setBeAllopatric(Boolean value) {
        this.beAllopatric = value;
    }

     
    public Boolean isBeGroupBuy() {
        return beGroupBuy;
    }

     
    public void setBeGroupBuy(Boolean value) {
        this.beGroupBuy = value;
    }

     
    public Boolean isBeImgageProject() {
        return beImgageProject;
    }

     
    public void setBeImgageProject(Boolean value) {
        this.beImgageProject = value;
    }

     
    public Boolean isBeManualSelectProjectFinish() {
        return beManualSelectProjectFinish;
    }

     
    public void setBeManualSelectProjectFinish(Boolean value) {
        this.beManualSelectProjectFinish = value;
    }

     
    public Boolean isBeMeetFirstParty() {
        return beMeetFirstParty;
    }

     
    public void setBeMeetFirstParty(Boolean value) {
        this.beMeetFirstParty = value;
    }

     
    public String getBeSendGpms() {
        return beSendGpms;
    }

     
    public void setBeSendGpms(String value) {
        this.beSendGpms = value;
    }

     
    public Boolean isBeStrategy() {
        return beStrategy;
    }

     
    public void setBeStrategy(Boolean value) {
        this.beStrategy = value;
    }

     
    public Boolean isBeTurnStraightGuest() {
        return beTurnStraightGuest;
    }

     
    public void setBeTurnStraightGuest(Boolean value) {
        this.beTurnStraightGuest = value;
    }

     
    public String getBigAndSmall() {
        return bigAndSmall;
    }

     
    public void setBigAndSmall(String value) {
        this.bigAndSmall = value;
    }

     
    public String getBrandInfoCode() {
        return brandInfoCode;
    }

     
    public void setBrandInfoCode(String value) {
        this.brandInfoCode = value;
    }

     
    public String getBrandInfoName() {
        return brandInfoName;
    }

     
    public void setBrandInfoName(String value) {
        this.brandInfoName = value;
    }

     
    public String getCancelPersonCode() {
        return cancelPersonCode;
    }

     
    public void setCancelPersonCode(String value) {
        this.cancelPersonCode = value;
    }

     
    public String getCancelReason() {
        return cancelReason;
    }

     
    public void setCancelReason(String value) {
        this.cancelReason = value;
    }

     
    public XMLGregorianCalendar getCancelTime() {
        return cancelTime;
    }

     
    public void setCancelTime(XMLGregorianCalendar value) {
        this.cancelTime = value;
    }

     
    public String getCenter() {
        return center;
    }

     
    public void setCenter(String value) {
        this.center = value;
    }

     
    public String getCleanEnergy() {
        return cleanEnergy;
    }

     
    public void setCleanEnergy(String value) {
        this.cleanEnergy = value;
    }

     
    public String getCleanEnergyType() {
        return cleanEnergyType;
    }

     
    public void setCleanEnergyType(String value) {
        this.cleanEnergyType = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getDataSource() {
        return dataSource;
    }

     
    public void setDataSource(String value) {
        this.dataSource = value;
    }

     
    public String getDistrictCode() {
        return districtCode;
    }

     
    public void setDistrictCode(String value) {
        this.districtCode = value;
    }

     
    public String getDomainModel() {
        return domainModel;
    }

     
    public void setDomainModel(String value) {
        this.domainModel = value;
    }

     
    public String getDomainType() {
        return domainType;
    }

     
    public void setDomainType(String value) {
        this.domainType = value;
    }

     
    public XMLGregorianCalendar getEstimatedTimeBid() {
        return estimatedTimeBid;
    }

     
    public void setEstimatedTimeBid(XMLGregorianCalendar value) {
        this.estimatedTimeBid = value;
    }

     
    public XMLGregorianCalendar getEstimatedWinningTime() {
        return estimatedWinningTime;
    }

     
    public void setEstimatedWinningTime(XMLGregorianCalendar value) {
        this.estimatedWinningTime = value;
    }

     
    public String getEucId() {
        return eucId;
    }

     
    public void setEucId(String value) {
        this.eucId = value;
    }

     
    public String getFirstCompanyId() {
        return firstCompanyId;
    }

     
    public void setFirstCompanyId(String value) {
        this.firstCompanyId = value;
    }

     
    public String getFirstCompanyName() {
        return firstCompanyName;
    }

     
    public void setFirstCompanyName(String value) {
        this.firstCompanyName = value;
    }

     
    public String getFirstCompanyOrgCode() {
        return firstCompanyOrgCode;
    }

     
    public void setFirstCompanyOrgCode(String value) {
        this.firstCompanyOrgCode = value;
    }

     
    public Boolean isFrequencyConversion() {
        return frequencyConversion;
    }

     
    public void setFrequencyConversion(Boolean value) {
        this.frequencyConversion = value;
    }

     
    public String getFunnelStage() {
        return funnelStage;
    }

     
    public void setFunnelStage(String value) {
        this.funnelStage = value;
    }

     
    public XMLGregorianCalendar getFunnelStageUpdateTime() {
        return funnelStageUpdateTime;
    }

     
    public void setFunnelStageUpdateTime(XMLGregorianCalendar value) {
        this.funnelStageUpdateTime = value;
    }

     
    public String getGridCenterCode() {
        return gridCenterCode;
    }

     
    public void setGridCenterCode(String value) {
        this.gridCenterCode = value;
    }

     
    public String getGridCenterName() {
        return gridCenterName;
    }

     
    public void setGridCenterName(String value) {
        this.gridCenterName = value;
    }

     
    public String getGridCode() {
        return gridCode;
    }

     
    public void setGridCode(String value) {
        this.gridCode = value;
    }

     
    public String getGridName() {
        return gridName;
    }

     
    public void setGridName(String value) {
        this.gridName = value;
    }

     
    public String getGridUserCode() {
        return gridUserCode;
    }

     
    public void setGridUserCode(String value) {
        this.gridUserCode = value;
    }

     
    public String getGridUserName() {
        return gridUserName;
    }

     
    public void setGridUserName(String value) {
        this.gridUserName = value;
    }

     
    public String getGroupId() {
        return groupId;
    }

     
    public void setGroupId(String value) {
        this.groupId = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getIndustryHomeCategory() {
        return industryHomeCategory;
    }

     
    public void setIndustryHomeCategory(String value) {
        this.industryHomeCategory = value;
    }

     
    public Boolean isIsEditing() {
        return isEditing;
    }

     
    public void setIsEditing(Boolean value) {
        this.isEditing = value;
    }

     
    public Boolean isJudgmentSheetStatus() {
        return judgmentSheetStatus;
    }

     
    public void setJudgmentSheetStatus(Boolean value) {
        this.judgmentSheetStatus = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getLesFlag() {
        return lesFlag;
    }

     
    public void setLesFlag(String value) {
        this.lesFlag = value;
    }

     
    public String getLesMsg() {
        return lesMsg;
    }

     
    public void setLesMsg(String value) {
        this.lesMsg = value;
    }

     
    public Integer getLoginLevel() {
        return loginLevel;
    }

     
    public void setLoginLevel(Integer value) {
        this.loginLevel = value;
    }

     
    public String getMsgId() {
        return msgId;
    }

     
    public void setMsgId(String value) {
        this.msgId = value;
    }

     
    public String getProductSeriesOneCode() {
        return productSeriesOneCode;
    }

     
    public void setProductSeriesOneCode(String value) {
        this.productSeriesOneCode = value;
    }

     
    public String getProductSeriesOneName() {
        return productSeriesOneName;
    }

     
    public void setProductSeriesOneName(String value) {
        this.productSeriesOneName = value;
    }

     
    public String getProductSeriesTwoCode() {
        return productSeriesTwoCode;
    }

     
    public void setProductSeriesTwoCode(String value) {
        this.productSeriesTwoCode = value;
    }

     
    public String getProductSeriesTwoName() {
        return productSeriesTwoName;
    }

     
    public void setProductSeriesTwoName(String value) {
        this.productSeriesTwoName = value;
    }

     
    public String getProfitSharing() {
        return profitSharing;
    }

     
    public void setProfitSharing(String value) {
        this.profitSharing = value;
    }

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public String getProjectCreaterCode() {
        return projectCreaterCode;
    }

     
    public void setProjectCreaterCode(String value) {
        this.projectCreaterCode = value;
    }

     
    public String getProjectCreaterId() {
        return projectCreaterId;
    }

     
    public void setProjectCreaterId(String value) {
        this.projectCreaterId = value;
    }

     
    public String getProjectCreaterName() {
        return projectCreaterName;
    }

     
    public void setProjectCreaterName(String value) {
        this.projectCreaterName = value;
    }

     
    public XMLGregorianCalendar getProjectFinishDate() {
        return projectFinishDate;
    }

     
    public void setProjectFinishDate(XMLGregorianCalendar value) {
        this.projectFinishDate = value;
    }

     
    public XMLGregorianCalendar getProjectLastModifiedDate() {
        return projectLastModifiedDate;
    }

     
    public void setProjectLastModifiedDate(XMLGregorianCalendar value) {
        this.projectLastModifiedDate = value;
    }

     
    public String getProjectManagerCenter() {
        return projectManagerCenter;
    }

     
    public void setProjectManagerCenter(String value) {
        this.projectManagerCenter = value;
    }

     
    public String getProjectManagerCode() {
        return projectManagerCode;
    }

     
    public void setProjectManagerCode(String value) {
        this.projectManagerCode = value;
    }

     
    public String getProjectManagerId() {
        return projectManagerId;
    }

     
    public void setProjectManagerId(String value) {
        this.projectManagerId = value;
    }

     
    public String getProjectManagerName() {
        return projectManagerName;
    }

     
    public void setProjectManagerName(String value) {
        this.projectManagerName = value;
    }

     
    public String getProjectName() {
        return projectName;
    }

     
    public void setProjectName(String value) {
        this.projectName = value;
    }

     
    public String getProjectSource() {
        return projectSource;
    }

     
    public void setProjectSource(String value) {
        this.projectSource = value;
    }

     
    public String getProjectStatus() {
        return projectStatus;
    }

     
    public void setProjectStatus(String value) {
        this.projectStatus = value;
    }

     
    public String getProjectType() {
        return projectType;
    }

     
    public void setProjectType(String value) {
        this.projectType = value;
    }

     
    public List<ProjectPurchaseForecastSaveParam> getPurchaseForecastList() {
        if (purchaseForecastList == null) {
            purchaseForecastList = new ArrayList<ProjectPurchaseForecastSaveParam>();
        }
        return this.purchaseForecastList;
    }

     
    public String getSalesModel() {
        return salesModel;
    }

     
    public void setSalesModel(String value) {
        this.salesModel = value;
    }

     
    public String getSamplePlateType() {
        return samplePlateType;
    }

     
    public void setSamplePlateType(String value) {
        this.samplePlateType = value;
    }

     
    public String getSendGpmsResult() {
        return sendGpmsResult;
    }

     
    public void setSendGpmsResult(String value) {
        this.sendGpmsResult = value;
    }

     
    public XMLGregorianCalendar getSendGpmsTime() {
        return sendGpmsTime;
    }

     
    public void setSendGpmsTime(XMLGregorianCalendar value) {
        this.sendGpmsTime = value;
    }

     
    public List<ProjectSKMaglevSaveParam> getSkMaglevList() {
        if (skMaglevList == null) {
            skMaglevList = new ArrayList<ProjectSKMaglevSaveParam>();
        }
        return this.skMaglevList;
    }

     
    public String getStrategyId() {
        return strategyId;
    }

     
    public void setStrategyId(String value) {
        this.strategyId = value;
    }

}
