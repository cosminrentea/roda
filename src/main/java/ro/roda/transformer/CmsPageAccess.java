package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONSerializer;

@Configurable
public class CmsPageAccess extends JsonInfo {

	public static String toJsonArray(Collection<CmsPageAccess> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("timestamp", "ipaddr", "userid", "username", "referer", "useragent");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<CmsPageAccess> findAllCmsPageAccesses(HttpServletRequest request) {
		List<CmsPageAccess> result = new ArrayList<CmsPageAccess>();

		@SuppressWarnings("deprecation")
		// TODO; Date.parse(request.getHeader("timestamp")) + userid, username
		CmsPageAccess access = new CmsPageAccess(null, request.getHeader("Ipaddr"), null, null,
				request.getHeader("Referer"), request.getHeader("User-Agent"));

		result.add(access);

		return result;
	}

	/*
	 * public static CmsPageAccess findCmsPageInfo(Integer id) { CmsPage page =
	 * CmsPage.findCmsPage(id);
	 * 
	 * if (page != null) { CmsPageAccess cmsPageInfo = new CmsPageAccess(page);
	 * boolean leaf = (page.getCmsPages() == null ? true :
	 * page.getCmsPages().size() == 0); cmsPageInfo.setLeaf(leaf); return
	 * cmsPageInfo; }
	 * 
	 * return null; }
	 */

	private Date timestamp;

	private String ipaddr;

	private Integer userid;

	private String username;

	private String referer;

	private String useragent;

	public CmsPageAccess(Date timestamp, String ipaddr, Integer userid, String username, String referer,
			String useragent) {
		this.timestamp = timestamp;
		this.ipaddr = ipaddr;
		this.userid = userid;
		this.username = username;
		this.referer = referer;
		this.useragent = useragent;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("timestamp", "ipaddr", "userid", "username", "referer", "useragent");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
