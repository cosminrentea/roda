package ro.roda.service.page;

import ro.fortsoft.pf4j.ExtensionPoint;

public interface Page extends ExtensionPoint {
	public String getPageContent(String url);
}
