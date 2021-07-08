package app.finology.service;

import app.finology.exception.CrawlerBaseException;
import app.finology.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Ebrahim Kh.
 */


@Slf4j
public class PageParser {

    public static Document parse(String url) {
        if (url == null && !UrlUtil.isUrl(url)) {
            throw new CrawlerBaseException(String.format("for url: [%s] can not create document.", url));
        }
        return getDocumentByUrl(url);
    }


    protected static Document getDocumentByHtml(String html) {
        Document jsoupDoc = Jsoup.parse(html, "UTF-8", Parser.htmlParser());
        jsoupDoc.charset(StandardCharsets.UTF_8);
        return jsoupDoc;
    }

    protected static Document getDocumentByUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new CrawlerBaseException(String.format("for url: [%s] ---> exception: [%s]", url, e.getMessage()));
        }
    }

    protected static Elements getElementsByQuery(Document html, String query) {
        log.debug("looking for element: {}", query);
        var elements = html.select(query);
        if (elements != null) {
            return elements;
        }
        throw new CrawlerBaseException(String.format("There isn't the element ( %s )", query));
    }

    protected static Elements getElementsByTag(Document html, String tag) {
        log.debug("looking for element by tag: {}", tag);
        var elements = html.getElementsByTag(tag);
        if (elements != null) {
            return elements;
        }
        throw new CrawlerBaseException(String.format("There isn't the element by tag ( %s )", tag));
    }


    protected static Set<String> findLinks(Document html) {
        Elements hrefElements = getElementsByQuery(html, "a[href]");
        Set<String> links = new HashSet<String>();
        if (hrefElements != null && hrefElements.size() > 0) {
            links = hrefElements.stream()
                .map(item -> item.attr("abs:href")) // href、abs:href
                .filter(UrlUtil::isUrl)
                .collect(Collectors.toSet());
        }
        return links;
    }

    protected static Set<String> findImages(Document html) {
        Elements imgs = getElementsByTag(html, "img");
        Set<String> images = new HashSet<String>();
        if (imgs != null && imgs.size() > 0) {
            for (Element element : imgs) {
                String imgSrc = element.attr("abs:src");
                images.add(imgSrc);
            }
        }
        return images;
    }

}

