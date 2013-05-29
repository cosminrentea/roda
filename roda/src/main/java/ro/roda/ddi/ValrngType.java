//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.05 at 02:34:54 AM EEST 
//

package ro.roda.ddi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for valrngType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="valrngType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="range" type="{http://www.icpsr.umich.edu/DDI}rangeType"/>
 *           &lt;element name="item" type="{http://www.icpsr.umich.edu/DDI}itemType"/>
 *         &lt;/choice>
 *         &lt;element name="key" type="{http://www.icpsr.umich.edu/DDI}keyType" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.icpsr.umich.edu/DDI}notesType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
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
@XmlType(name = "valrngType", propOrder = { "range", "item" })
@Entity
@Table(schema = "ddi",name = "ValueRange")
public class ValrngType {
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
	@JoinColumn(name = "variableDDI_id")
	@XmlTransient
	private VarType vartype;

	public VarType getVartype() {
		return vartype;
	}

	public void setVartype(VarType vartype) {
		this.vartype = vartype;
	}

	// TODO Cosmin replaced original List "rangeOrItem" with 2 Lists: one with
	// "ItemType, one with "RangeType"
	// @XmlElements({
	// @XmlElement(name = "item", type = ItemType.class),
	// @XmlElement(name = "range", type = RangeType.class)
	// })
	// @ElementCollection protected List<String> rangeOrItem;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "valrngtype")
	protected List<RangeType> range;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "valrngtype")
	protected List<ItemType> item;

	@XmlAttribute(name = "ID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	// @XmlAttribute(name = "xml-lang")
	// @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	// @XmlSchemaType(name = "NMTOKEN")
	// protected String xmlLang;
	// @XmlAttribute
	// @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	// protected String source;

	/**
	 * Gets the value of the rangeOrItem property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live List, not a
	 * snapshot. Therefore any modification you make to the returned List will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the rangeOrItem property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getRangeOrItem().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the List {@link ItemType }
	 * {@link RangeType }
	 * 
	 * 
	 */
	// public List<String> getRangeOrItem() {
	// if (rangeOrItem == null) {
	// rangeOrItem = new ArrayList<String>();
	// }
	// return this.rangeOrItem;
	// }

	public List<RangeType> getRange() {
		if (range == null) {
			range = new ArrayList<RangeType>();
		}
		return this.range;
	}

	public List<ItemType> getItem() {
		if (item == null) {
			item = new ArrayList<ItemType>();
		}
		return this.item;
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

}
