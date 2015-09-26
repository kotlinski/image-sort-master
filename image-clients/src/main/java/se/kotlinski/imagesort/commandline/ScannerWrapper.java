package se.kotlinski.imagesort.commandline;

import java.util.Scanner;

public class ScannerWrapper {
  private final Scanner inScanner;

  public ScannerWrapper() {
    this.inScanner = new Scanner(System.in, "UTF-8");
  }

  public final String nextLine() {
    return inScanner.nextLine();
  }
}
