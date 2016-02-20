package se.kotlinski.imagesort.module;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.google.inject.AbstractModule;
import com.mixpanel.mixpanelapi.MessageBuilder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import se.kotlinski.imagesort.commandline.listeners.ImageSortMoveFeedbackProgressFeedback;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.secrets.Passwords;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ImageModule extends AbstractModule {

  @Override
  protected final void configure() {
    bind(MoveFeedbackInterface.class).to(ImageSortMoveFeedbackProgressFeedback.class);
    bind(CommandLineParser.class).to(GnuParser.class);
    bind(Calendar.class).to(GregorianCalendar.class);
    bind(GoogleAnalytics.class).toInstance(new GoogleAnalytics(new Passwords().getAnalytics()));
    bind(MessageBuilder.class).toInstance(new MessageBuilder(new Passwords().getMixpanelToken()));
  }
}