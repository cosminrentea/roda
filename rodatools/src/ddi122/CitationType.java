//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.05 at 02:34:54 AM EEST 
//

package ddi122;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for citationType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="citationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titlStmt" type="{http://www.icpsr.umich.edu/DDI}titlStmtType"/>
 *         &lt;element name="rspStmt" type="{http://www.icpsr.umich.edu/DDI}rspStmtType" minOccurs="0"/>
 *         &lt;element name="prodStmt" type="{http://www.icpsr.umich.edu/DDI}prodStmtType" minOccurs="0"/>
 *         &lt;element name="distStmt" type="{http://www.icpsr.umich.edu/DDI}distStmtType" minOccurs="0"/>
 *         &lt;element name="serStmt" type="{http://www.icpsr.umich.edu/DDI}serStmtType" minOccurs="0"/>
 *         &lt;element name="verStmt" type="{http://www.icpsr.umich.edu/DDI}verStmtType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="biblCit" type="{http://www.icpsr.umich.edu/DDI}biblCitType" minOccurs="0"/>
 *         &lt;element name="holdings" type="{http://www.icpsr.umich.edu/DDI}holdingsType" maxOccurs="unbounded" minOccurs="0"/>
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
 *       &lt;attribute name="MARCURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "citationType", propOrder = { "titlStmt", "rspStmt",
		"prodStmt", "distStmt", "serStmt", "verStmt", "biblCit", "holdings" })
@Entity
@Table(name = "Citation")
public class CitationType {
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
	@JoinColumn(name = "stdydscrtype_id")
	@XmlTransient
	private StdyDscrType stdydscrtype;

	public StdyDscrType getStdydscrtype() {
		return stdydscrtype;
	}

	public void setStdydscrtype(StdyDscrType stdydscrtype) {
		this.stdydscrtype = stdydscrtype;
	}

	@ManyToOne
	@JoinColumn(name = "relmattype_id")
	@XmlTransient
	private RelMatType relmattype;

	public RelMatType getRelmattype() {
		return relmattype;
	}

	public void setRelmattype(RelMatType relmattype) {
		this.relmattype = relmattype;
	}

	@XmlElement(required = true)
	@OneToOne(cascade = { CascadeType.ALL })
	protected TitlStmtType titlStmt;

	@OneToOne(cascade = { CascadeType.ALL })
	protected RspStmtType rspStmt;

	@OneToOne(cascade = { CascadeType.ALL })
	protected ProdStmtType prodStmt;

	@OneToOne(cascade = { CascadeType.ALL })
	protected DistStmtType distStmt;

	@OneToOne(cascade = { CascadeType.ALL })
	protected SerStmtType serStmt;

	@OneToMany(mappedBy = "citationtype", cascade = { CascadeType.ALL })
	protected List<VerStmtType> verStmt;

	@OneToOne(cascade = { CascadeType.ALL })
	protected BiblCitType biblCit;

	@OneToMany(mappedBy = "citationtype", cascade = { CascadeType.ALL })
	protected List<HoldingsType> holdings;

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
	@XmlAttribute(name = "MARCURI")
	protected String marcuri;

	/**
	 * Gets the value of the titlStmt property.
	 * 
	 * @return possible object is {@link TitlStmtType }
	 * 
	 */
	public TitlStmtType getTitlStmt() {
		return titlStmt;
	}

	/**
	 * Sets the value of the titlStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link TitlStmtType }
	 * 
	 */
	public void setTitlStmt(TitlStmtType value) {
		this.titlStmt = value;
	}

	/**
	 * Gets the value of the rspStmt property.
	 * 
	 * @return possible object is {@link RspStmtType }
	 * 
	 */
	public RspStmtType getRspStmt() {
		return rspStmt;
	}

	/**
	 * Sets the value of the rspStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link RspStmtType }
	 * 
	 */
	public void setRspStmt(RspStmtType value) {
		this.rspStmt = value;
	}

	/**
	 * Gets the value of the prodStmt property.
	 * 
	 * @return possible object is {@link ProdStmtType }
	 * 
	 */
	public ProdStmtType getProdStmt() {
		return prodStmt;
	}

	/**
	 * Sets the value of the prodStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link ProdStmtType }
	 * 
	 */
	public void setProdStmt(ProdStmtType value) {
		this.prodStmt = value;
	}

	/**
	 * Gets the value of the distStmt property.
	 * 
	 * @return possible object is {@link DistStmtType }
	 * 
	 */
	public DistStmtType getDistStmt() {
		return distStmt;
	}

	/**
	 * Sets the value of the distStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link DistStmtType }
	 * 
	 */
	public void setDistStmt(DistStmtType value) {
		this.distStmt = value;
	}

	/**
	 * Gets the value of the serStmt property.
	 * 
	 * @return possible object is {@link SerStmtType }
	 * 
	 */
	public SerStmtType getSerStmt() {
		return serStmt;
	}

	/**
	 * Sets the value of the serStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link SerStmtType }
	 * 
	 */
	public void setSerStmt(SerStmtType value) {
		this.serStmt = value;
	}

	/**
	 * Gets the value of the verStmt property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live List, not a
	 * snapshot. Therefore any modification you make to the returned List will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the verStmt property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getVerStmt().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the List
	 * {@link VerStmtType }
	 * 
	 * 
	 */
	public List<VerStmtType> getVerStmt() {
		if (verStmt == null) {
			verStmt = new ArrayList<VerStmtType>();
		}
		return this.verStmt;
	}

	/**
	 * Gets the value of the biblCit property.
	 * 
	 * @return possible object is {@link BiblCitType }
	 * 
	 */
	public BiblCitType getBiblCit() {
		return biblCit;
	}

	/**
	 * Sets the value of the biblCit property.
	 * 
	 * @param value
	 *            allowed object is {@link BiblCitType }
	 * 
	 */
	public void setBiblCit(BiblCitType value) {
		this.biblCit = value;
	}

	/**
	 * Gets the value of the holdings property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live List, not a
	 * snapshot. Therefore any modification you make to the returned List will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the holdings property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getHoldings().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the List
	 * {@link HoldingsType }
	 * 
	 * 
	 */
	public List<HoldingsType> getHoldings() {
		if (holdings == null) {
			holdings = new ArrayList<HoldingsType>();
		}
		return this.holdings;
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

	/**
	 * Gets the value of the marcuri property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMARCURI() {
		return marcuri;
	}

	/**
	 * Sets the value of the marcuri property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMARCURI(String value) {
		this.marcuri = value;
	}

	@Override
	public String toString() {
		return "CitationType [id_=" + id_ + ", titlStmt=" + titlStmt
				+ ", rspStmt=" + rspStmt + ", prodStmt=" + prodStmt
				+ ", distStmt=" + distStmt + ", serStmt=" + serStmt
				+ ", verStmt=" + verStmt + ", biblCit=" + biblCit
				+ ", holdings=" + holdings + ", id=" + id + ", xmlLang="
				+ xmlLang + ", source=" + source + ", marcuri=" + marcuri + "]";
	}

}
