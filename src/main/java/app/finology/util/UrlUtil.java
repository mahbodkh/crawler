package app.finology.util;


import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ebrahim Kh.
 */

@Slf4j
public class UrlUtil {
    public static boolean isUrl(String url) {
        return (url != null && !url.trim().isEmpty()) && (url.startsWith("http") || url.startsWith("https"));
    }

    public static boolean isUrlByRegex(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        return matches(url);
    }

    private static boolean matches(String str) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private static final String URL_REGEX = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
}
