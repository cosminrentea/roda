package org.opendatafoundation.data.spss;

/*
 * Author(s): Pascal Heus (pheus@opendatafoundation.org)
 *  
 * This product has been developed with the financial and 
 * technical support of the UK Data Archive Data Exchange Tools 
 * project (http://www.data-archive.ac.uk/dext/) and the 
 * Open Data Foundation (http://www.opendatafoundation.org) 
 * 
 * Copyright 2007 University of Essex (http://www.esds.ac.uk) 
 * 
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library; if not, write to the 
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301  USA
 * The full text of the license is also available on the Internet at 
 * http://www.gnu.org/copyleft/lesser.html
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

/**
 * SPSS Record Type 6 - Document record
 * 
 * @author Pascal Heus (pheus@opendatafoundation.org)
 */
@Entity
public class SPSSRecordType6 extends SPSSAbstractRecordType {

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

	int recordTypeCode;

	int numberOfLines;

	@ElementCollection
	List<String> line;

	public void read(SPSSFile is) throws IOException, SPSSFileException {
		// position in file
		fileLocation = is.getFilePointer();

		// record type
		recordTypeCode = is.readSPSSInt();
		if (recordTypeCode != 6)
			throw new SPSSFileException(
					"Error reading variableRecord: bad record type ["
							+ recordTypeCode + "]. Expecting Record Type 6.");
		// number of variables
		numberOfLines = is.readSPSSInt();
		// read the lines
		line = new ArrayList<String>();
		for (int i = 0; i < numberOfLines; i++) {
			line.add(is.readSPSSString(80));
		}
	}

	public String toString() {
		String str = "";
		str += "\nRECORD TYPE 6 - DOCUMENT RECORD";
		str += "\nLocation        : " + fileLocation;
		str += "\nRecord Type     : " + recordTypeCode;
		str += "\nNumber of lines : " + numberOfLines;
		for (int i = 0; i < line.size(); i++) {
			str += line.get(i) + "\n";
		}
		return (str);
	}
}
