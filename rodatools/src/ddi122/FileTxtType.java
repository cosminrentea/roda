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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.opendatafoundation.data.spss.SPSSFile;

/**
 * <p>
 * Java class for fileTxtType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="fileTxtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.icpsr.umich.edu/DDI}fileNameType" minOccurs="0"/>
 *         &lt;element name="fileCont" type="{http://www.icpsr.umich.edu/DDI}fileContType" minOccurs="0"/>
 *         &lt;element name="fileStrc" type="{http://www.icpsr.umich.edu/DDI}fileStrcType" minOccurs="0"/>
 *         &lt;element name="dimensns" type="{http://www.icpsr.umich.edu/DDI}dimensnsType" minOccurs="0"/>
 *         &lt;element name="fileType" type="{http://www.icpsr.umich.edu/DDI}fileTypeType" minOccurs="0"/>
 *         &lt;element name="format" type="{http://www.icpsr.umich.edu/DDI}formatType" minOccurs="0"/>
 *         &lt;element name="filePlac" type="{http://www.icpsr.umich.edu/DDI}filePlacType" minOccurs="0"/>
 *         &lt;element name="dataChck" type="{http://www.icpsr.umich.edu/DDI}dataChckType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ProcStat" type="{http://www.icpsr.umich.edu/DDI}ProcStatType" minOccurs="0"/>
 *         &lt;element name="dataMsng" type="{http://www.icpsr.umich.edu/DDI}dataMsngType" minOccurs="0"/>
 *         &lt;element name="software" type="{http://www.icpsr.umich.edu/DDI}softwareType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verStmt" type="{http://www.icpsr.umich.edu/DDI}verStmtType" minOccurs="0"/>
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
@XmlType(name = "fileTxtType", propOrder = { "fileName", "dimensns",
		"fileType", "software", "verStmt" })
@Entity
@Table(name = "DataFileDescription")
public class FileTxtType {
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

	@XmlTransient
	@OneToOne(cascade = { CascadeType.ALL })
	private SPSSFile spssfile;

	public SPSSFile getSpssfile() {
		return spssfile;
	}

	public void setSpssfile(SPSSFile spssfile) {
		this.spssfile = spssfile;
	}

	@ManyToOne
	@JoinColumn(name = "DataFilesDescription_id")
	@XmlTransient
	private FileDscrType filedscrtype;

	public FileDscrType getFiledscrtype() {
		return filedscrtype;
	}

	public void setFiledscrtype(FileDscrType filedscrtype) {
		this.filedscrtype = filedscrtype;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	protected FileNameType fileName;

	@OneToOne(cascade = { CascadeType.ALL })
	protected DimensnsType dimensns;

	@OneToOne(cascade = { CascadeType.ALL })
	protected FileTypeType fileType;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "softwaretype")
	protected List<SoftwareType> software;

	@OneToOne(cascade = { CascadeType.ALL })
	protected VerStmtType verStmt;

	@XmlAttribute(name = "ID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	// @XmlAttribute(name = "xml-lang")
	// @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	// @XmlSchemaType(name = "NMTOKEN")
	// protected String xmlLang;
	//
	// @XmlAttribute
	// @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	// protected String source;

	/**
	 * Gets the value of the fileName property.
	 * 
	 * @return possible object is {@link FileNameType }
	 * 
	 */
	public FileNameType getFileName() {
		return fileName;
	}

	/**
	 * Sets the value of the fileName property.
	 * 
	 * @param value
	 *            allowed object is {@link FileNameType }
	 * 
	 */
	public void setFileName(FileNameType value) {
		this.fileName = value;
	}

	/**
	 * Gets the value of the dimensns property.
	 * 
	 * @return possible object is {@link DimensnsType }
	 * 
	 */
	public DimensnsType getDimensns() {
		return dimensns;
	}

	/**
	 * Sets the value of the dimensns property.
	 * 
	 * @param value
	 *            allowed object is {@link DimensnsType }
	 * 
	 */
	public void setDimensns(DimensnsType value) {
		this.dimensns = value;
	}

	/**
	 * Gets the value of the fileType property.
	 * 
	 * @return possible object is {@link FileTypeType }
	 * 
	 */
	public FileTypeType getFileType() {
		return fileType;
	}

	/**
	 * Sets the value of the fileType property.
	 * 
	 * @param value
	 *            allowed object is {@link FileTypeType }
	 * 
	 */
	public void setFileType(FileTypeType value) {
		this.fileType = value;
	}

	/**
	 * Gets the value of the software property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live List, not a
	 * snapshot. Therefore any modification you make to the returned List will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the software property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSoftware().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the List
	 * {@link SoftwareType }
	 * 
	 * 
	 */
	public List<SoftwareType> getSoftware() {
		if (software == null) {
			software = new ArrayList<SoftwareType>();
		}
		return this.software;
	}

	/**
	 * Gets the value of the verStmt property.
	 * 
	 * @return possible object is {@link VerStmtType }
	 * 
	 */
	public VerStmtType getVerStmt() {
		return verStmt;
	}

	/**
	 * Sets the value of the verStmt property.
	 * 
	 * @param value
	 *            allowed object is {@link VerStmtType }
	 * 
	 */
	public void setVerStmt(VerStmtType value) {
		this.verStmt = value;
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
