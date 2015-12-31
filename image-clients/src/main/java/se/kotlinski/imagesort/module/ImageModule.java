package se.kotlinski.imagesort.module;

import com.google.inject.AbstractModule;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import se.kotlinski.imagesort.commandline.ImageSortProgressFeedback;
import se.kotlinski.imagesort.executor.ClientInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ImageModule extends AbstractModule {

  @Override
  protected final void configure() {
    bind(ClientInterface.class).to(ImageSortProgressFeedback.class);
    bind(CommandLineParser.class).to(GnuParser.class);
    bind(Calendar.class).to(GregorianCalendar.class);
  }
}