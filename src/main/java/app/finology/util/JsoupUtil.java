package app.finology.util;

import app.finology.conf.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;


/**
 * Created by Ebrahim Kh.
 */


@Slf4j
public class JsoupUtil {

    public static String parseElement(Element fieldElement, BrowserConfig.SelectType selectType, String selectVal) {
        String fieldElementOrigin = null;
        if (BrowserConfig.SelectType.HTML == selectType) {
            fieldElementOrigin = fieldElement.html();
        } else if (BrowserConfig.SelectType.VAL == selectType) {
            fieldElementOrigin = fieldElement.val();
        } else if (BrowserConfig.SelectType.TEXT == selectType) {
            fieldElementOrigin = fieldElement.text();
        } else if (BrowserConfig.SelectType.ATTR == selectType) {
            fieldElementOrigin = fieldElement.attr(selectVal);
        } else if (BrowserConfig.SelectType.HAS_CLASS == selectType) {
            fieldElementOrigin = String.valueOf(fieldElement.hasClass(selectVal));
        } else {
            fieldElementOrigin = fieldElement.toString();
        }
        return fieldElementOrigin;
    }

}
