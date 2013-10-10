import junit.framework.TestCase;
import org.junit.After;
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
public class FileRenamerTest extends TestCase {
    private FileRenamer _fileRenamer;
    private static final String SOURCEFOLDER = "sourceFolderTest";
    private File sourceFolder;

    @Before
    public void setUp() throws Exception {
        System.out.println("--- Setup ---");
        _fileRenamer = new FileRenamer();
        sourceFolder = createFolder(System.getProperty("user.dir") + "\\sourceFolderTest");

        System.out.println(sourceFolder.getPath());

        _fileRenamer.setSourceFolder(sourceFolder.getPath());
        _fileRenamer.setDestinationFolder(sourceFolder.getPath());

    }


    private File createFolder(String fileName){
        File theDir = new File(fileName);
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();
            if(result) {
                System.out.println("DIR created");
            }
        } else {
            theDir.listFiles();
            for(File file : theDir.listFiles()){
                file.delete();
            }
        }
        return theDir;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsDropboxFormat() throws Exception {
        String dropboxName = "2013-09-18 16.18.45.jpg";
        assertTrue(_fileRenamer.isDropboxFormat(dropboxName));
        assertFalse(_fileRenamer.isDropboxFormat("asfd-fe3-fe fewf.wef"));
    }

    @Test
    public void testDropboxToSamsungFormat() throws Exception {
        String dropboxName = "2013-09-18 16.18.45.jpg";

        String samsungName = _fileRenamer.dropboxToSamsungFormat(dropboxName);
        assertEquals("20130918_161845.jpg", samsungName);
    }
    @Test
    public void testSamsungToDropboxFormat() throws Exception {
        String samsungName = "20130918_161845.jpg";
        String dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        assertEquals("2013-09-18 16.18.45.jpg", dropboxName);

        samsungName = "20120101_080702.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        assertEquals("2012-01-01 08.07.02.jpg", dropboxName);


        samsungName = "2012-12-25 00.34.29.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        assertEquals("2012-12-25 00.34.29.jpg", dropboxName);

        samsungName = "20121202_132658.jpg";
        dropboxName = _fileRenamer.samsungToDropboxFormat(samsungName);
        assertEquals("2012-12-02 13.26.58.jpg", dropboxName);

    }

    @Test
    public void testExtension() throws Exception {

        String extension = _fileRenamer.getExtension("sdfasd.asdfasdf.jpeg");
        assertEquals(extension, ".jpeg");
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
        _fileRenamer.renameFiles();
        assertNotNull(sourceFolder.listFiles());
        File[] listFiles = sourceFolder.listFiles();
        if(listFiles != null)
        {
            assertEquals(listFiles.length, 3);
            for(File file : listFiles){
                System.out.println("Filename: " + file.getName());
                assertTrue(_fileRenamer.isSamsungFormat(file.getName()) || _fileRenamer.isDropboxFormat(file.getName()));
            }
        }
    }
}
