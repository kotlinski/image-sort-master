package se.kotlinski.imagesort.resolver;

import se.kotlinski.imagesort.utils.MD5Generator;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class OutputConflictResolver {
  final MD5Generator md5Generator;

  public OutputConflictResolver(final MD5Generator md5Generator) {
    this.md5Generator = md5Generator;
  }

  public enum Destany {
    MOVE, DELETE, IGNORE
  }

  public void resolveOutputConflicts(final Map<String, List<File>> mediaFileDestinations) {
    Map<File, String> resolvedDestinations;
    for (String outputDestination : mediaFileDestinations.keySet()) {
      List<DestanyFilePath> destanyFiles = resolveConflicts(mediaFileDestinations.get(
          outputDestination));
      //    System.out.println(outputDestination);
      //   System.out.println(mediaFileDestinations.get(outputDestination));
      //  System.out.println();
    }
  }

  private List<DestanyFilePath> resolveConflicts(final List<File> files) {
    Stack<File> stack = new Stack<>();
    stack.addAll(files);

    //TODO 2015 -12 - 09
    //What we really want to do here is to group files.
    // Groups of they who need to be renamed
    // Gropus of they who can be directly translated.

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
  }
}
