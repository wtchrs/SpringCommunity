package wtchrs.SpringCommunity.view.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LocalDateTimeFormatter formatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(formatter);
    }

    @Component
    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime object, Locale locale) {
            return formatter.format(object);
        }
    }
}
