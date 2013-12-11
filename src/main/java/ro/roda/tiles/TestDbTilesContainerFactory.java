package ro.roda.tiles;

import java.util.Locale;

import javax.sql.DataSource;

import org.apache.tiles.definition.LocaleDefinitionsFactory;
import org.apache.tiles.definition.dao.DefinitionDAO;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.locale.LocaleResolver;
import org.apache.tiles.request.ApplicationContext;

/**
 * Test alternate Tiles container factory that uses a DB to store definitions.
 * 
 * @version $Rev$ $Date$
 */
public class TestDbTilesContainerFactory extends BasicTilesContainerFactory {

	/** {@inheritDoc} */
	@Override
	protected DefinitionDAO<Locale> createLocaleDefinitionDao(ApplicationContext applicationContext,
			LocaleResolver resolver) {
		LocaleDbDefinitionDAO definitionDao = new LocaleDbDefinitionDAO();
		definitionDao.setDataSource((DataSource) applicationContext.getApplicationScope().get("dataSource"));
		return definitionDao;
	}

	/** {@inheritDoc} */
	@Override
	protected LocaleDefinitionsFactory instantiateDefinitionsFactory(ApplicationContext applicationContext,
			LocaleResolver resolver) {
		return new LocaleDefinitionsFactory();
	}
}
