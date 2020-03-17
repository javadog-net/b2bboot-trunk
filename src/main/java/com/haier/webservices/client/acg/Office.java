
package com.haier.webservices.client.acg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>office complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="office"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}treeEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="area" type="{http://acg.server.webservices.haier.com/}area" minOccurs="0"/&gt;
 *         &lt;element name="childDeptList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deputyPerson" type="{http://acg.server.webservices.haier.com/}user" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="grade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="master" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parent" type="{http://acg.server.webservices.haier.com/}office" minOccurs="0"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="primaryPerson" type="{http://acg.server.webservices.haier.com/}user" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="useable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="zipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "office", propOrder = {
    "rest"
})
public class Office
    extends TreeEntity
{

    @XmlElementRefs({
        @XmlElementRef(name = "fax", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "type", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "address", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "deputyPerson", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "primaryPerson", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "email", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "phone", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "parent", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "zipCode", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "code", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "grade", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "master", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "area", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "childDeptList", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "useable", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> rest;

    /**
     * ??????????????????
     * 
     * <p>
     * ???????????, ?????????? "catch-all" ????: 
     * ??????? "Parent" ???????????????????á??????: 
     * http://localhost:8090/soap/acg?wsdl=AcgPushService.wsdl#types1??? 0 ??
     * http://localhost:8090/soap/acg?wsdl=AcgPushService.wsdl#types1??? 0 ??
     * <p>
     * ??????????, ??????????????????????????????
     * ????????????????????: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link User }{@code >}
     * {@link JAXBElement }{@code <}{@link User }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Office }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Area }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<?>>();
        }
        return this.rest;
    }

}
