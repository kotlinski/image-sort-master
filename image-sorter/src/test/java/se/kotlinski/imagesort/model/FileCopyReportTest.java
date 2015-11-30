package se.kotlinski.imagesort.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileCopyReportTest {

  @Test
  public void testToString() throws Exception {
    FileCopyReport fileCopyReport = new FileCopyReport();
    fileCopyReport.fileCopySuccess();
    DeprecatedFileDescriber fileDescriberMock = mock(DeprecatedFileDescriber.class);
    when(fileDescriberMock.getOriginalFileName()).thenReturn("mock-name");
    fileCopyReport.fileCopyFailed(fileDescriberMock);
    String expected = "Files Copied: 1\n" +
                      "Files failed: \n" +
                      "mock-name\n";
    assertEquals("Test to string, 1 success, 1 fail", expected, fileCopyReport.toString());
  }
}