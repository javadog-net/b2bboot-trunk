
package com.haier.webservices.client.acg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.webservices.client.acg package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PushContractor_QNAME = new QName("http://acg.server.webservices.haier.com/", "pushContractor");
    private final static QName _PushContractorResponse_QNAME = new QName("http://acg.server.webservices.haier.com/", "pushContractorResponse");
    private final static QName _AreaCode_QNAME = new QName("", "code");
    private final static QName _AreaParent_QNAME = new QName("", "parent");
    private final static QName _AreaType_QNAME = new QName("", "type");
    private final static QName _OfficeAddress_QNAME = new QName("", "address");
    private final static QName _OfficeArea_QNAME = new QName("", "area");
    private final static QName _OfficeChildDeptList_QNAME = new QName("", "childDeptList");
    private final static QName _OfficeDeputyPerson_QNAME = new QName("", "deputyPerson");
    private final static QName _OfficeEmail_QNAME = new QName("", "email");
    private final static QName _OfficeFax_QNAME = new QName("", "fax");
    private final static QName _OfficeGrade_QNAME = new QName("", "grade");
    private final static QName _OfficeMaster_QNAME = new QName("", "master");
    private final static QName _OfficePhone_QNAME = new QName("", "phone");
    private final static QName _OfficePrimaryPerson_QNAME = new QName("", "primaryPerson");
    private final static QName _OfficeUseable_QNAME = new QName("", "useable");
    private final static QName _OfficeZipCode_QNAME = new QName("", "zipCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.webservices.client.acg
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AjaxJson }
     * 
     */
    public AjaxJson createAjaxJson() {
        return new AjaxJson();
    }

    /**
     * Create an instance of {@link AjaxJson.Body }
     * 
     */
    public AjaxJson.Body createAjaxJsonBody() {
        return new AjaxJson.Body();
    }

    /**
     * Create an instance of {@link PushContractor }
     * 
     */
    public PushContractor createPushContractor() {
        return new PushContractor();
    }

    /**
     * Create an instance of {@link PushContractorResponse }
     * 
     */
    public PushContractorResponse createPushContractorResponse() {
        return new PushContractorResponse();
    }

    /**
     * Create an instance of {@link Dealer }
     * 
     */
    public Dealer createDealer() {
        return new Dealer();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Office }
     * 
     */
    public Office createOffice() {
        return new Office();
    }

    /**
     * Create an instance of {@link Area }
     * 
     */
    public Area createArea() {
        return new Area();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link DataRule }
     * 
     */
    public DataRule createDataRule() {
        return new DataRule();
    }

    /**
     * Create an instance of {@link Menu }
     * 
     */
    public Menu createMenu() {
        return new Menu();
    }

    /**
     * Create an instance of {@link AjaxJson.Body.Entry }
     * 
     */
    public AjaxJson.Body.Entry createAjaxJsonBodyEntry() {
        return new AjaxJson.Body.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PushContractor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://acg.server.webservices.haier.com/", name = "pushContractor")
    public JAXBElement<PushContractor> createPushContractor(PushContractor value) {
        return new JAXBElement<PushContractor>(_PushContractor_QNAME, PushContractor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PushContractorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://acg.server.webservices.haier.com/", name = "pushContractorResponse")
    public JAXBElement<PushContractorResponse> createPushContractorResponse(PushContractorResponse value) {
        return new JAXBElement<PushContractorResponse>(_PushContractorResponse_QNAME, PushContractorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "code", scope = Area.class)
    public JAXBElement<String> createAreaCode(String value) {
        return new JAXBElement<String>(_AreaCode_QNAME, String.class, Area.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Area }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "parent", scope = Area.class)
    public JAXBElement<Area> createAreaParent(Area value) {
        return new JAXBElement<Area>(_AreaParent_QNAME, Area.class, Area.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "type", scope = Area.class)
    public JAXBElement<String> createAreaType(String value) {
        return new JAXBElement<String>(_AreaType_QNAME, String.class, Area.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "address", scope = Office.class)
    public JAXBElement<String> createOfficeAddress(String value) {
        return new JAXBElement<String>(_OfficeAddress_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Area }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "area", scope = Office.class)
    public JAXBElement<Area> createOfficeArea(Area value) {
        return new JAXBElement<Area>(_OfficeArea_QNAME, Area.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "childDeptList", scope = Office.class)
    public JAXBElement<String> createOfficeChildDeptList(String value) {
        return new JAXBElement<String>(_OfficeChildDeptList_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "code", scope = Office.class)
    public JAXBElement<String> createOfficeCode(String value) {
        return new JAXBElement<String>(_AreaCode_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "deputyPerson", scope = Office.class)
    public JAXBElement<User> createOfficeDeputyPerson(User value) {
        return new JAXBElement<User>(_OfficeDeputyPerson_QNAME, User.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "email", scope = Office.class)
    public JAXBElement<String> createOfficeEmail(String value) {
        return new JAXBElement<String>(_OfficeEmail_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fax", scope = Office.class)
    public JAXBElement<String> createOfficeFax(String value) {
        return new JAXBElement<String>(_OfficeFax_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "grade", scope = Office.class)
    public JAXBElement<String> createOfficeGrade(String value) {
        return new JAXBElement<String>(_OfficeGrade_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "master", scope = Office.class)
    public JAXBElement<String> createOfficeMaster(String value) {
        return new JAXBElement<String>(_OfficeMaster_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Office }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "parent", scope = Office.class)
    public JAXBElement<Office> createOfficeParent(Office value) {
        return new JAXBElement<Office>(_AreaParent_QNAME, Office.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "phone", scope = Office.class)
    public JAXBElement<String> createOfficePhone(String value) {
        return new JAXBElement<String>(_OfficePhone_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "primaryPerson", scope = Office.class)
    public JAXBElement<User> createOfficePrimaryPerson(User value) {
        return new JAXBElement<User>(_OfficePrimaryPerson_QNAME, User.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "type", scope = Office.class)
    public JAXBElement<String> createOfficeType(String value) {
        return new JAXBElement<String>(_AreaType_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "useable", scope = Office.class)
    public JAXBElement<String> createOfficeUseable(String value) {
        return new JAXBElement<String>(_OfficeUseable_QNAME, String.class, Office.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "zipCode", scope = Office.class)
    public JAXBElement<String> createOfficeZipCode(String value) {
        return new JAXBElement<String>(_OfficeZipCode_QNAME, String.class, Office.class, value);
    }

}
