package se.kotlinski.imagesort.resolver;

import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputConflictResolver {
  private final MD5Generator md5Generator;
  private final MediaFileUtil mediaFileUtil;


  public OutputConflictResolver(final MD5Generator md5Generator,
                                final MediaFileUtil mediaFileUtil) {
    this.md5Generator = md5Generator;
    this.mediaFileUtil = mediaFileUtil;
  }

  public enum Destany {
    MOVE, DELETE, IGNORE
  }

  public Map<List<File>, String> resolveOutputConflicts(final Map<String, List<File>> mediaFileDestinations) {
    Map<List<File>, String> filesToOutputDestination = new HashMap<>();

    for (String outputDirectory : mediaFileDestinations.keySet()) {
      List<File> files = mediaFileDestinations.get(outputDirectory);

      Map<String, List<File>> md5Groups = new HashMap<>();
      for (File file : files) {
        String fileMd5 = md5Generator.generateMd5(file);
        if (!md5Groups.containsKey(fileMd5)) {
          md5Groups.put(fileMd5, new ArrayList<>());
        }
        md5Groups.get(fileMd5).add(file);
      }

      if (severalGroupsWithSameOutput(md5Groups)) {
        int append = 1;
        for (List<File> fileList : md5Groups.values()) {
          String outputWithAppendedValue = mediaFileUtil.appendToFileName(outputDirectory, "_" + append);
          filesToOutputDestination.put(fileList, outputWithAppendedValue);
          append++;
        }
      }
      else {
        filesToOutputDestination.put(files, outputDirectory);
      }
    }

    return filesToOutputDestination;


    /*Map<File, String> resolvedDestinations;
    for (String outputDestination : mediaFileDestinations.keySet()) {
      List<DestanyFilePath> destanyFiles = resolveConflicts(mediaFileDestinations.get(
          outputDestination));
    */  //    System.out.println(outputDestination);
    //   System.out.println(mediaFileDestinations.get(outputDestination));
    //  System.out.println();
  }

  private boolean severalGroupsWithSameOutput(final Map<String, List<File>> md5Groups) {
    return md5Groups.size() > 1;
  }

/*
  private List<DestanyFilePath> resolveConflicts(final List<File> files) {
    Stack<File> stack = new Stack<>();
    stack.addAll(files);

    //TODO 2015 -12 - 09
    //What we really want to do here is to group files.
    // Groups of they who need to be renamed
    // Gropus of they who can be directly translated/moved.

    //So,,,
    // What we really want to export is a Map<List<File>>, String>.
    // Each List<file> will have an output.

    // If two files have the same output,
    //     then one of them should be moved and the the other one should be deleted

    // If two different files have same output
    //      then append _1, _2 after the file names.

    // If

    Set<File> filesToMerge = findDuplicates(stack);
    stack.addAll(files);
    Set<File> filesToAppendSpecifier = findUniques(stack);

    return null;
  }

  private Set<File> findUniques(final Stack<File> stack) {
    Set<File> uniques = new HashSet<>();
    for (File file : stack) {
      System.out.println(file);
    }
    System.out.println("....");
    while (!stack.empty()) {

      File pop = stack.pop();
      String orginalMd5 = md5Generator.generateMd5(pop);

      for (File file : stack) {
        String fileMd5 = md5Generator.generateMd5(file);
        if (!fileMd5.equals(orginalMd5)) {
          System.out.println(stack);
          uniques.add(pop);
          uniques.add(file);
          System.out.println(pop);
          System.out.println(file);
          System.out.println();
        }
      }
    }

    return uniques;
  }

  private Set<File> findDuplicates(final Stack<File> stack) {
    File pop = stack.pop();
    Set<File> duplicates = new HashSet<>();

    String orginalMd5 = md5Generator.generateMd5(pop);
    for (File file : stack) {
      String fileMd5 = md5Generator.generateMd5(file);
      if (fileMd5.equals(orginalMd5)) {
        duplicates.add(pop);
        duplicates.add(file);
      }
    }
    System.out.println();

    return duplicates;
  }


  public class DestanyFilePath {
    final Destany destionation;
    final String filePath;

    public DestanyFilePath(final Destany destionation, final String filePath) {
      this.destionation = destionation;
      this.filePath = filePath;
    }
  }*/
}
