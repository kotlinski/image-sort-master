package se.kotlinski.imagesort.module;

import com.google.inject.AbstractModule;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import se.kotlinski.imagesort.controller.CmdController;
import se.kotlinski.imagesort.controller.FileExecutor;
import se.kotlinski.imagesort.controller.IController;
import se.kotlinski.imagesort.controller.IFileExecutor;

public class ImageModule extends AbstractModule {
  @Override
  protected void configure() {

     /*
      * This tells Guice that whenever it sees a dependency on a TransactionLog,
      * it should satisfy the dependency using a DatabaseTransactionLog.
      */
    bind(IController.class).to(CmdController.class);
    bind(IFileExecutor.class).to(FileExecutor.class);
    bind(CommandLineParser.class).to(GnuParser.class);
  }
}