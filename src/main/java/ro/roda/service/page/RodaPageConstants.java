package ro.roda.service.page;

public class RodaPageConstants {

	// values like: "" OR "/p" OR "/page"
	public final static String PAGE_MAPPING = "";

	// this depends on the URL mapping of "RodaPageController":
	// /roda => /roda/j/cmsfilecontent
	// /roda/page => /roda/page/../j/cmsfilecontent !!!
	public static String CMS_FILE_CONTENT_URL = "cmsfilecontent/";
	public static int OFFSET_RELATIVE_URL = 1;

	public static String DEFAULT_LANGUAGE_URL = "/en";
	public static String DEFAULT_ERROR_PAGE_LANGUAGE = "en";
	public static String ADMIN_URL = "admin/index.html";

	// CMS Codes (to be replaced / expanded)
	public static String PAGE_TITLE_CODE = "[[Code: PageTitle]]";
	public static String PAGE_LINK_BY_URL_CODE = "[[Code: PageLinkbyUrl('";
	public static String PAGE_URL_LINK_CODE = "[[PageURLLink:";
	public static String FILE_URL_LINK_CODE = "[[FileURL:";
	public static String IMG_LINK_CODE = "[[ImgLink: ";
	public static String SNIPPET_CODE = "[[Snippet: ";
	public static String PAGE_CONTENT_CODE = "[[Code: PageContent]]";
	public static String PAGE_TREE_BY_URL_CODE = "[[Code: PageTreeByUrl('";
	public static String PAGE_BREADCRUMBS_CODE = "[[Code: PageBreadcrumbs('";
	public static String GETNEWS_CODE = "[[Code: GetNews";
	public static String GETNEWSID_CODE = "[[Code: GetNewsId";

}
