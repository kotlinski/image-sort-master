import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-09-12
 * Time: 08:15
 *
 * Sets up a sourceFolderTest for test, with some files that I test to rename to Samsung Format.
 *
 */
public class FileRenamerTest {
    private FileRenamer _fileRenamer;
    private File sourceFolder;

	@Before
    public void setUp() throws Exception {
        _fileRenamer = new FileRenamer();
        sourceFolder = createFolder(System.getProperty("user.dir") + "\\sourceFolderTest");

        _fileRenamer.setSourceFolder(sourceFolder.getPath());
        _fileRenamer.setDestinationFolder(sourceFolder.getPath());
    }
    private File createFolder(String fileName){
        File theDir = new File(fileName);
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();
            if(result) {
            }
        } else {
            File[] list = theDir.listFiles();
            boolean deleted = false;
            if(list != null){
                for (File file : list) {
                    deleted = file.delete();
                }
            }
            if(deleted)
            {
            }

        }
        return theDir;
    }

    @After
    public void tearDown() throws Exception {
		FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "\\sourceFolderTest"));
    }

    @Test
    public void testIsDropboxFormat() throws Exception {
        String dropboxName = "2013-09-18 16.18.45.jpg";
        Assert.assertTrue(_fileRenamer.isDropboxFormat(dropboxName));
        Assert.assertFalse(_fileRenamer.isDropboxFormat("asfd-fe3-fe fewf.wef"));
    }

    @Test
    public void testDropboxToSamsungFormat() throws Exception {
        String dropboxName = "2013-09-18 16.18.45.jpg";

        String samsungName = _fileRenamer.dropboxToSamsungFormat(dropboxName);
        Assert.assertEquals("20130918_161845.jpg", samsungName);
    }

    @Test
    public void testSamsungToDropboxFormat() throws Exception {
        String samsungName = "20130918_161845.jpg";
        String dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        Assert.assertEquals("2013-09-18 16.18.45.jpg", dropboxName);

        samsungName = "20120101_080702.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        Assert.assertEquals("2012-01-01 08.07.02.jpg", dropboxName);


        samsungName = "2012-12-25 00.34.29.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        Assert.assertEquals("2012-12-25 00.34.29.jpg", dropboxName);

        samsungName = "20121202_132658.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        Assert.assertEquals("2012-12-02 13.26.58.jpg", dropboxName);

    }

    @Test
    public void testExtension() throws Exception {

        String extension = _fileRenamer.getExtension("sdfasd.asdfasdf.jpeg");
        Assert.assertEquals(extension, ".jpeg");
    }
    @Test
    public void testRenameFiles() throws Exception {
        File samsungFile1 = new File(sourceFolder.getPath() + "\\20130802_130634.jpg");

        File dropboxFile1 = new File(sourceFolder.getPath() + "\\2013-08-02 13.06.33.jpg");
        File dropboxFile2 = new File(sourceFolder.getPath() + "\\2013-08-02 13.06.34.jpg");

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(dropboxFile1));
            output.write("asdf asd  db1");
            output.close();

            output = new BufferedWriter(new FileWriter(dropboxFile2));
            output.write("asdf asd  db2");
            output.close();

            output = new BufferedWriter(new FileWriter(samsungFile1));
            output.write("asdf asd");
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        _fileRenamer.renameFiles(Enums.FORMAT.DROPBOX, Enums.FORMAT.SAMSUNG);
        Assert.assertNotNull(sourceFolder.listFiles());
        File[] listFiles = sourceFolder.listFiles();
        if(listFiles != null)
        {
            Assert.assertEquals(listFiles.length, 3);
            for(File file : listFiles){
                Assert.assertTrue(_fileRenamer.isSamsungFormat(file.getName()) || _fileRenamer.isDropboxFormat(file.getName()));
            }
        }
    }
}
