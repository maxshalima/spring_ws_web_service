//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.02 at 02:31:45 PM FET 
//


package by.test.webservices.recordservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VocabloryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vocabloryName",
    "content"
})
@XmlRootElement(name = "AddRecordToVocabloryRequest")
public class AddRecordToVocabloryRequest {

    @XmlElement(name = "VocabloryName", required = true)
    protected String vocabloryName;
    @XmlElement(name = "Content")
    protected long content;

    /**
     * Gets the value of the vocabloryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVocabloryName() {
        return vocabloryName;
    }

    /**
     * Sets the value of the vocabloryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVocabloryName(String value) {
        this.vocabloryName = value;
    }

    /**
     * Gets the value of the content property.
     * 
     */
    public long getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     */
    public void setContent(long value) {
        this.content = value;
    }

}
