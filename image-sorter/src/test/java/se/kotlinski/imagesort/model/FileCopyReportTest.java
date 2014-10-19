package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.ImageDescriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileCopyReportTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testToString() throws Exception {
    FileCopyReport fileCopyReport = new FileCopyReport();
    fileCopyReport.fileCopySuccess();
    ImageDescriber imageDescriberMock = mock(ImageDescriber.class);
    when(imageDescriberMock.getOriginalFileName()).thenReturn("mock-name");
    fileCopyReport.fileCopyFailed(imageDescriberMock);
    String expected = "Files Copied: 1\n" +
                      "Files failed: \n" +
                      "mock-name\n";
    assertEquals("Test to string, 1 success, 1 fail", expected, fileCopyReport.toString());
  }
}