package ro.roda.service.page;

import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

public class RodaPagePlugin extends Plugin {

	private static RodaPageService rodaPageService;

	public RodaPagePlugin(PluginWrapper wrapper) {
		super(wrapper);
	}

	@Extension
	public static class RodaPage implements Page {

		public String getPageContent(String url) {
			return rodaPageService.generatePage(url)[1];
		}
	}
}
