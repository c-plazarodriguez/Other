//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.01.28 um 03:29:13 PM CET 
//


package de.bund.bfr.epcis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * 
 *       MasterData document that contains a Header and a Body.
 *               
 * 
 * <p>Java-Klasse f�r EPCISMasterDataDocumentType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="EPCISMasterDataDocumentType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:epcglobal:xsd:1}Document">
 *       &lt;sequence>
 *         &lt;element name="EPCISHeader" type="{urn:epcglobal:epcis:xsd:1}EPCISHeaderType" minOccurs="0"/>
 *         &lt;element name="EPCISBody" type="{urn:epcglobal:epcis-masterdata:xsd:1}EPCISMasterDataBodyType"/>
 *         &lt;element name="extension" type="{urn:epcglobal:epcis-masterdata:xsd:1}EPCISMasterDataDocumentExtensionType" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EPCISMasterDataDocumentType", namespace = "urn:epcglobal:epcis-masterdata:xsd:1", propOrder = {
    "epcisHeader",
    "epcisBody",
    "extension",
    "any"
})
public class EPCISMasterDataDocumentType
    extends Document
{

    @XmlElement(name = "EPCISHeader")
    protected EPCISHeaderType epcisHeader;
    @XmlElement(name = "EPCISBody", required = true)
    protected EPCISMasterDataBodyType epcisBody;
    protected EPCISMasterDataDocumentExtensionType extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Ruft den Wert der epcisHeader-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EPCISHeaderType }
     *     
     */
    public EPCISHeaderType getEPCISHeader() {
        return epcisHeader;
    }

    /**
     * Legt den Wert der epcisHeader-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EPCISHeaderType }
     *     
     */
    public void setEPCISHeader(EPCISHeaderType value) {
        this.epcisHeader = value;
    }

    /**
     * Ruft den Wert der epcisBody-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EPCISMasterDataBodyType }
     *     
     */
    public EPCISMasterDataBodyType getEPCISBody() {
        return epcisBody;
    }

    /**
     * Legt den Wert der epcisBody-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EPCISMasterDataBodyType }
     *     
     */
    public void setEPCISBody(EPCISMasterDataBodyType value) {
        this.epcisBody = value;
    }

    /**
     * Ruft den Wert der extension-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EPCISMasterDataDocumentExtensionType }
     *     
     */
    public EPCISMasterDataDocumentExtensionType getExtension() {
        return extension;
    }

    /**
     * Legt den Wert der extension-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EPCISMasterDataDocumentExtensionType }
     *     
     */
    public void setExtension(EPCISMasterDataDocumentExtensionType value) {
        this.extension = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
