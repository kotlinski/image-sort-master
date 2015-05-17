package se.kotlinski.imagesort.commandline;

import java.util.Scanner;

public class ScannerWrapper {
  private final Scanner inScanner;

  public ScannerWrapper() {
    this.inScanner = new Scanner(System.in);
  }

  public String nextLine() {
    return inScanner.nextLine();
  }
}
