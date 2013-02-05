package ro.roda;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = CmsFile.class)
public class CmsFileIntegrationTest {

	@Autowired
	private CmsFileDataOnDemand dod;

	@Autowired
	private CmsFolderDataOnDemand dodFolder;

	@Test
	public void testMarkerMethod() {
	}

	@Test
	public void testCreateFileinFolder() {
		CmsFolder folder = dodFolder
				.getNewTransientCmsFolder(Integer.MAX_VALUE);
		Assert.assertNotNull(
				"Data on demand for 'CmsFolder' failed to initialize correctly",
				folder);

		CmsFile file = dod.getNewTransientCmsFile(Integer.MAX_VALUE);
		Assert.assertNotNull(
				"Data on demand for 'CmsFile' failed to initialize correctly",
				file);

		long countFolders = CmsFolder.countCmsFolders();
		long countFiles = CmsFile.countCmsFiles();

		folder.persist();
		folder.flush();

		file.addToFolder(folder.getName(), file.getFilename());

		file.persist();
		file.flush();

		Assert.assertEquals("No CmsFolder was created",
				CmsFolder.countCmsFolders(), countFolders + 1);
		Assert.assertEquals("No CmsFile was created", CmsFile.countCmsFiles(),
				countFiles + 1);
		Assert.assertNotNull("No Link set", folder.getCmsFiles());
		Assert.assertEquals("Wrong number of files", folder.getCmsFiles()
				.size(), 1);

	}

}
