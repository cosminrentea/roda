package ro.roda.tiles;

import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.startup.AbstractTilesInitializer;

/**
 * Test Tiles initializer for Tiles initialization of the db-based container.
 * 
 * @version $Rev$ $Date$
 */
public class TestDbTilesInitializer extends AbstractTilesInitializer {

	/** {@inheritDoc} */
	@Override
	protected AbstractTilesContainerFactory createContainerFactory(ApplicationContext context) {
		return new TestDbTilesContainerFactory();
	}

	/** {@inheritDoc} */
	@Override
	protected String getContainerKey(ApplicationContext applicationContext) {
		return "db";
	}
}