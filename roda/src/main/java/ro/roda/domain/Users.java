package ro.roda.domain;

import java.io.Serializable;
import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@Audited @RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "users", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Users 
implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
