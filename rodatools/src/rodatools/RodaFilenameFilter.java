package rodatools;

import java.io.File;
import java.io.FilenameFilter;

public class RodaFilenameFilter implements FilenameFilter {

	String extension;

	public RodaFilenameFilter(String extension) {
		super();
		this.extension = extension;
	}

	@Override
	public boolean accept(File f, String s) {
		if (s.toUpperCase().endsWith(extension))
			return true;
		return false;
	}

}