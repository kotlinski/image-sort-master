package se.kotlinski.imagesort.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeprecatedFileCopyReportTest {

  @Test
  public void testToString() throws Exception {
    DeprecatedFileCopyReport deprecatedFileCopyReport = new DeprecatedFileCopyReport();
    deprecatedFileCopyReport.fileCopySuccess();
    DeprecatedFileDescriber deprecatedFileDescriberMock = mock(DeprecatedFileDescriber.class);
    when(deprecatedFileDescriberMock.getOriginalFileName()).thenReturn("mock-name");
    deprecatedFileCopyReport.fileCopyFailed(deprecatedFileDescriberMock);
    String expected = "Files Copied: 1\n" +
                      "Files failed: \n" +
                      "mock-name\n";
    assertEquals("Test to string, 1 success, 1 fail", expected, deprecatedFileCopyReport.toString());
  }
}