/**
 * HAIERMDMFIELDS_VALUE_TYPE.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.searchcust2mdm;

public class HAIERMDMFIELDS_VALUE_TYPE  implements java.io.Serializable {
    private java.lang.String FIELD_NAME;

    private java.lang.String FIELD_VALUE;

    private java.lang.String FIELD_QUERY_TYPE;

    public HAIERMDMFIELDS_VALUE_TYPE() {
    }

    public HAIERMDMFIELDS_VALUE_TYPE(
           java.lang.String FIELD_NAME,
           java.lang.String FIELD_VALUE,
           java.lang.String FIELD_QUERY_TYPE) {
           this.FIELD_NAME = FIELD_NAME;
           this.FIELD_VALUE = FIELD_VALUE;
           this.FIELD_QUERY_TYPE = FIELD_QUERY_TYPE;
    }


    /**
     * Gets the FIELD_NAME value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @return FIELD_NAME
     */
    public java.lang.String getFIELD_NAME() {
        return FIELD_NAME;
    }


    /**
     * Sets the FIELD_NAME value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @param FIELD_NAME
     */
    public void setFIELD_NAME(java.lang.String FIELD_NAME) {
        this.FIELD_NAME = FIELD_NAME;
    }


    /**
     * Gets the FIELD_VALUE value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @return FIELD_VALUE
     */
    public java.lang.String getFIELD_VALUE() {
        return FIELD_VALUE;
    }


    /**
     * Sets the FIELD_VALUE value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @param FIELD_VALUE
     */
    public void setFIELD_VALUE(java.lang.String FIELD_VALUE) {
        this.FIELD_VALUE = FIELD_VALUE;
    }


    /**
     * Gets the FIELD_QUERY_TYPE value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @return FIELD_QUERY_TYPE
     */
    public java.lang.String getFIELD_QUERY_TYPE() {
        return FIELD_QUERY_TYPE;
    }


    /**
     * Sets the FIELD_QUERY_TYPE value for this HAIERMDMFIELDS_VALUE_TYPE.
     * 
     * @param FIELD_QUERY_TYPE
     */
    public void setFIELD_QUERY_TYPE(java.lang.String FIELD_QUERY_TYPE) {
        this.FIELD_QUERY_TYPE = FIELD_QUERY_TYPE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HAIERMDMFIELDS_VALUE_TYPE)) return false;
        HAIERMDMFIELDS_VALUE_TYPE other = (HAIERMDMFIELDS_VALUE_TYPE) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.FIELD_NAME==null && other.getFIELD_NAME()==null) || 
             (this.FIELD_NAME!=null &&
              this.FIELD_NAME.equals(other.getFIELD_NAME()))) &&
            ((this.FIELD_VALUE==null && other.getFIELD_VALUE()==null) || 
             (this.FIELD_VALUE!=null &&
              this.FIELD_VALUE.equals(other.getFIELD_VALUE()))) &&
            ((this.FIELD_QUERY_TYPE==null && other.getFIELD_QUERY_TYPE()==null) || 
             (this.FIELD_QUERY_TYPE!=null &&
              this.FIELD_QUERY_TYPE.equals(other.getFIELD_QUERY_TYPE())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFIELD_NAME() != null) {
            _hashCode += getFIELD_NAME().hashCode();
        }
        if (getFIELD_VALUE() != null) {
            _hashCode += getFIELD_VALUE().hashCode();
        }
        if (getFIELD_QUERY_TYPE() != null) {
            _hashCode += getFIELD_QUERY_TYPE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HAIERMDMFIELDS_VALUE_TYPE.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "HAIERMDM.FIELDS_VALUE_TYPE"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIELD_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIELD_VALUE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_VALUE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIELD_QUERY_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_QUERY_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
