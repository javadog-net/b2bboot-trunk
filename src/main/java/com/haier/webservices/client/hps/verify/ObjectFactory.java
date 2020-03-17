
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


 
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetVerifyBillFromBrown_QNAME = new QName("http://verify.manage.hps.com/", "getVerifyBillFromBrown");
    private final static QName _GetVerifyBillFromBrownResponse_QNAME = new QName("http://verify.manage.hps.com/", "getVerifyBillFromBrownResponse");
    private final static QName _QueryPage_QNAME = new QName("http://verify.manage.hps.com/", "queryPage");
    private final static QName _QueryPageForVerify_QNAME = new QName("http://verify.manage.hps.com/", "queryPageForVerify");
    private final static QName _QueryPageForVerifyResponse_QNAME = new QName("http://verify.manage.hps.com/", "queryPageForVerifyResponse");
    private final static QName _QueryPageResponse_QNAME = new QName("http://verify.manage.hps.com/", "queryPageResponse");
    private final static QName _SaveOrUpdate_QNAME = new QName("http://verify.manage.hps.com/", "saveOrUpdate");
    private final static QName _SaveOrUpdateResponse_QNAME = new QName("http://verify.manage.hps.com/", "saveOrUpdateResponse");
    private final static QName _VerifyInfo_QNAME = new QName("http://verify.manage.hps.com/", "verifyInfo");
    private final static QName _VerifyInfoResponse_QNAME = new QName("http://verify.manage.hps.com/", "verifyInfoResponse");

     
    public ObjectFactory() {
    }

     
    public GetVerifyBillFromBrown createGetVerifyBillFromBrown() {
        return new GetVerifyBillFromBrown();
    }

     
    public GetVerifyBillFromBrownResponse createGetVerifyBillFromBrownResponse() {
        return new GetVerifyBillFromBrownResponse();
    }

     
    public QueryPage createQueryPage() {
        return new QueryPage();
    }

     
    public QueryPageForVerify createQueryPageForVerify() {
        return new QueryPageForVerify();
    }

     
    public QueryPageForVerifyResponse createQueryPageForVerifyResponse() {
        return new QueryPageForVerifyResponse();
    }

     
    public QueryPageResponse createQueryPageResponse() {
        return new QueryPageResponse();
    }

     
    public SaveOrUpdate createSaveOrUpdate() {
        return new SaveOrUpdate();
    }

     
    public SaveOrUpdateResponse createSaveOrUpdateResponse() {
        return new SaveOrUpdateResponse();
    }

     
    public VerifyInfo createVerifyInfo() {
        return new VerifyInfo();
    }

     
    public VerifyInfoResponse createVerifyInfoResponse() {
        return new VerifyInfoResponse();
    }

     
    public ResInfoVerifyInfo createResInfoVerifyInfo() {
        return new ResInfoVerifyInfo();
    }

     
    public ResInfo createResInfo() {
        return new ResInfo();
    }

     
    public VerifyBillVO createVerifyBillVO() {
        return new VerifyBillVO();
    }

     
    public VerifyBillDetailVO createVerifyBillDetailVO() {
        return new VerifyBillDetailVO();
    }

     
    public WebserviceBrownQueryPagePara createWebserviceBrownQueryPagePara() {
        return new WebserviceBrownQueryPagePara();
    }

     
    public ResInfoBrown createResInfoBrown() {
        return new ResInfoBrown();
    }

     
    public PageableVo createPageableVo() {
        return new PageableVo();
    }

     
    public BrownVO createBrownVO() {
        return new BrownVO();
    }

     
    public BrownCancelVO createBrownCancelVO() {
        return new BrownCancelVO();
    }

     
    public BrownDelayVO createBrownDelayVO() {
        return new BrownDelayVO();
    }

     
    public BrownItemVO createBrownItemVO() {
        return new BrownItemVO();
    }

     
    public WebserviceVerifyQueryPagePara createWebserviceVerifyQueryPagePara() {
        return new WebserviceVerifyQueryPagePara();
    }

     
    public ResInfoVerify createResInfoVerify() {
        return new ResInfoVerify();
    }

     
    public VerifyBillSaveParam createVerifyBillSaveParam() {
        return new VerifyBillSaveParam();
    }

     
    public BasicOperatorRequest createBasicOperatorRequest() {
        return new BasicOperatorRequest();
    }

     
    public VerifyBillDetailSaveParam createVerifyBillDetailSaveParam() {
        return new VerifyBillDetailSaveParam();
    }

     
    public ResInfoVerifyParamInfo createResInfoVerifyParamInfo() {
        return new ResInfoVerifyParamInfo();
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "getVerifyBillFromBrown")
    public JAXBElement<GetVerifyBillFromBrown> createGetVerifyBillFromBrown(GetVerifyBillFromBrown value) {
        return new JAXBElement<GetVerifyBillFromBrown>(_GetVerifyBillFromBrown_QNAME, GetVerifyBillFromBrown.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "getVerifyBillFromBrownResponse")
    public JAXBElement<GetVerifyBillFromBrownResponse> createGetVerifyBillFromBrownResponse(GetVerifyBillFromBrownResponse value) {
        return new JAXBElement<GetVerifyBillFromBrownResponse>(_GetVerifyBillFromBrownResponse_QNAME, GetVerifyBillFromBrownResponse.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "queryPage")
    public JAXBElement<QueryPage> createQueryPage(QueryPage value) {
        return new JAXBElement<QueryPage>(_QueryPage_QNAME, QueryPage.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "queryPageForVerify")
    public JAXBElement<QueryPageForVerify> createQueryPageForVerify(QueryPageForVerify value) {
        return new JAXBElement<QueryPageForVerify>(_QueryPageForVerify_QNAME, QueryPageForVerify.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "queryPageForVerifyResponse")
    public JAXBElement<QueryPageForVerifyResponse> createQueryPageForVerifyResponse(QueryPageForVerifyResponse value) {
        return new JAXBElement<QueryPageForVerifyResponse>(_QueryPageForVerifyResponse_QNAME, QueryPageForVerifyResponse.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "queryPageResponse")
    public JAXBElement<QueryPageResponse> createQueryPageResponse(QueryPageResponse value) {
        return new JAXBElement<QueryPageResponse>(_QueryPageResponse_QNAME, QueryPageResponse.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "saveOrUpdate")
    public JAXBElement<SaveOrUpdate> createSaveOrUpdate(SaveOrUpdate value) {
        return new JAXBElement<SaveOrUpdate>(_SaveOrUpdate_QNAME, SaveOrUpdate.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "saveOrUpdateResponse")
    public JAXBElement<SaveOrUpdateResponse> createSaveOrUpdateResponse(SaveOrUpdateResponse value) {
        return new JAXBElement<SaveOrUpdateResponse>(_SaveOrUpdateResponse_QNAME, SaveOrUpdateResponse.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "verifyInfo")
    public JAXBElement<VerifyInfo> createVerifyInfo(VerifyInfo value) {
        return new JAXBElement<VerifyInfo>(_VerifyInfo_QNAME, VerifyInfo.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://verify.manage.hps.com/", name = "verifyInfoResponse")
    public JAXBElement<VerifyInfoResponse> createVerifyInfoResponse(VerifyInfoResponse value) {
        return new JAXBElement<VerifyInfoResponse>(_VerifyInfoResponse_QNAME, VerifyInfoResponse.class, null, value);
    }

}
