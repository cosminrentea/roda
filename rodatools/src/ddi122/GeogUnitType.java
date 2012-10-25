//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.05 at 02:34:54 AM EEST 
//

package ddi122;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Type;

/**
 * <p>
 * Java class for geogUnitType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="geogUnitType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="txt" type="{http://www.icpsr.umich.edu/DDI}txtType"/>
 *         &lt;element name="concept" type="{http://www.icpsr.umich.edu/DDI}conceptType"/>
 *         &lt;element name="Link" type="{http://www.icpsr.umich.edu/DDI}LinkType"/>
 *         &lt;element name="ExtLink" type="{http://www.icpsr.umich.edu/DDI}ExtLinkType"/>
 *       &lt;/choice>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="xml-lang" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="source" default="producer">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="archive"/>
 *             &lt;enumeration value="producer"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geogUnitType", propOrder = { "content" })
@Entity
@Table(name = "GeographicUnit")
public class GeogUnitType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@XmlTransient
	private long id_;

	public long getId_() {
		return id_;
	}

	public void setId_(long id_) {
		this.id_ = id_;
	}

	@ManyToOne
	@JoinColumn(name = "sumdscrtype_id")
	@XmlTransient
	private SumDscrType sumdscrtype;

	public SumDscrType getSumdscrtype() {
		return sumdscrtype;
	}

	public void setSumdscrtype(SumDscrType sumdscrtype) {
		this.sumdscrtype = sumdscrtype;
	}

	@XmlElementRefs({ @XmlElementRef(name = "txt", namespace = "http://www.icpsr.umich.edu/DDI", type = JAXBElement.class)
	// ,
	// @XmlElementRef(name = "concept", namespace =
	// "http://www.icpsr.umich.edu/DDI", type = JAXBElement.class),
	// @XmlElementRef(name = "Link", namespace =
	// "http://www.icpsr.umich.edu/DDI", type = JAXBElement.class),
	// @XmlElementRef(name = "ExtLink", namespace =
	// "http://www.icpsr.umich.edu/DDI", type = JAXBElement.class)
	})
	@XmlMixed
	@ElementCollection
	@Type(type = "text")
	protected List<String> content;
	@XmlAttribute(name = "ID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "xml-lang")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NMTOKEN")
	protected String xmlLang;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String source;

	/**
	 * Gets the value of the content property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live List, not a
	 * snapshot. Therefore any modification you make to the returned List will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the content property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getContent().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the List
	 * {@link JAXBElement }{@code <}{@link ConceptType }{@code >}
	 * {@link JAXBElement }{@code <}{@link TxtType }{@code >} {@link JAXBElement }
	 * {@code <}{@link LinkType }{@code >} {@link String } {@link JAXBElement }
	 * {@code <}{@link ExtLinkType }{@code >}
	 * 
	 * 
	 */
	public List<String> getContent() {
		if (content == null) {
			content = new ArrayList<String>();
		}
		return this.content;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getID() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setID(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the xmlLang property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXmlLang() {
		return xmlLang;
	}

	/**
	 * Sets the value of the xmlLang property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXmlLang(String value) {
		this.xmlLang = value;
	}

	/**
	 * Gets the value of the source property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSource() {
		if (source == null) {
			return null;
		} else {
			return source;
		}
	}

	/**
	 * Sets the value of the source property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSource(String value) {
		this.source = value;
	}

}
