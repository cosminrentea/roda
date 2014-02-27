package ro.roda.plugin;

import ro.fortsoft.pf4j.ExtensionPoint;

public interface Page extends ExtensionPoint {
	public String getPageContent(String url);
}
