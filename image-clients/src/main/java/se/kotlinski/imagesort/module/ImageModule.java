package se.kotlinski.imagesort.module;

import com.google.inject.AbstractModule;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import se.kotlinski.imagesort.commandline.ImageSortMovePhaseProgressFeedback;
import se.kotlinski.imagesort.main.ClientMovePhaseInterface;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ImageModule extends AbstractModule {

  @Override
  protected final void configure() {
    bind(ClientMovePhaseInterface.class).to(ImageSortMovePhaseProgressFeedback.class);
    bind(CommandLineParser.class).to(GnuParser.class);
    bind(Calendar.class).to(GregorianCalendar.class);
  }
}