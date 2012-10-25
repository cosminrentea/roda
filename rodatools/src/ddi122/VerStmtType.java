//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.05 at 02:34:54 AM EEST 
//

package ddi122;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
 * Java class for verStmtType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="verStmtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="version" type="{http://www.icpsr.umich.edu/DDI}versionType" minOccurs="0"/>
 *         &lt;element name="verResp" type="{http://www.icpsr.umich.edu/DDI}verRespType" minOccurs="0"/>
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
@XmlType(name = "verStmtType", propOrder = { "version", "verResp" })
@Entity
@Table(name = "VersionStatement")
public class VerStmtType {
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
	@JoinColumn(name = "vartype_id")
	@XmlTransient
	private VarType vartype;

	public VarType getVartype() {
		return vartype;
	}

	public void setVartype(VarType vartype) {
		this.vartype = vartype;
	}

	@ManyToOne
	@JoinColumn(name = "citationtype_id")
	@XmlTransient
	private CitationType citationtype;

	public CitationType getCitationtype() {
		return citationtype;
	}

	public void setCitation(CitationType citationtype) {
		this.citationtype = citationtype;
	}

	@ManyToOne
	@JoinColumn(name = "docsrctype_id")
	@XmlTransient
	private DocSrcType docsrctype;

	public DocSrcType getDocsrctype() {
		return docsrctype;
	}

	public void setDocsrctype(DocSrcType docsrctype) {
		this.docsrctype = docsrctype;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	protected VersionType version;

	@OneToOne(cascade = { CascadeType.ALL })
	protected VerRespType verResp;

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
	 * Gets the value of the version property.
	 * 
	 * @return possible object is {@link VersionType }
	 * 
	 */
	public VersionType getVersion() {
		return version;
	}

	/**
	 * Sets the value of the version property.
	 * 
	 * @param value
	 *            allowed object is {@link VersionType }
	 * 
	 */
	public void setVersion(VersionType value) {
		this.version = value;
	}

	/**
	 * Gets the value of the verResp property.
	 * 
	 * @return possible object is {@link VerRespType }
	 * 
	 */
	public VerRespType getVerResp() {
		return verResp;
	}

	/**
	 * Sets the value of the verResp property.
	 * 
	 * @param value
	 *            allowed object is {@link VerRespType }
	 * 
	 */
	public void setVerResp(VerRespType value) {
		this.verResp = value;
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
