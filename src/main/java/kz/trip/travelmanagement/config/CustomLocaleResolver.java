package kz.trip.travelmanagement.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if (language == null || language.isEmpty()) {
            return Locale.getDefault(); // or set your default locale
        }
        return Locale.forLanguageTag(language);
    }
}
