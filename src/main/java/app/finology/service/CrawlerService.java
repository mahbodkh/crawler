package app.finology.service;

import app.finology.domain.Link;
import app.finology.domain.Product;
import app.finology.repository.LinkRepository;
import app.finology.repository.ProductRepository;
import app.finology.util.UrlUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Ebrahim Kh.
 */

@AllArgsConstructor
@Slf4j
@Service
public class CrawlerService {
    private final Set<String> cache = Collections.synchronizedSet(new HashSet<>());
    private static final String BASE_URL = "https://magento-test.finology.com.my";
    private static final String START_URL = BASE_URL + "/breathe-easy-tank.html";
    private final ProductRepository productRepository;
    private final LinkRepository linkRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        updateCache();
        process(START_URL);
    }

    private void updateCache() {
        cache.addAll(
            linkRepository.findAll()
                .stream().filter(Objects::nonNull).map(Link::getUrl).collect(Collectors.toSet())
        );
    }

    private void process(String url) {
        if (cache.add(url)) {                               // check validation by cache handler
            linkRepository.save(Link.getBasicLink(url));    // to avoid multi process
            log.info("Insert url to database: [{}]", url);
        }

        // prepare to get document & elements ;-)
        var document = PageParser.parse(url);
        var elements = PageParser.getElementsByQuery(document, "a[href]");

        // persistence true data [product] in db
        extractProduct(document)
            .map(product -> {
                Product save = productRepository.save(product);
                log.info("Product has been persisted by: [{}].", product);
                return save;
            });

        // filter the result
        elements.stream()
            .filter(element -> element.attr("abs:href").startsWith(BASE_URL))
            .filter(element -> UrlUtil.isUrlByRegex(element.attr("abs:href")))
            .filter(element -> element.attr("abs:href").endsWith(".html"))
            .filter(element -> !element.attr("abs:href").contains("#"))
            .filter(element -> !cache.contains(element.attr("abs:href")))
            .forEach(element -> process(element.attr("abs:href")));
    }


    private Optional<Product> extractProduct(Document document) {
        String title = null, description = null, details = null;
        Double price = null;
        var productTitle = document.select("div.product-info-main");
        if (productTitle == null || productTitle.isEmpty()) return Optional.empty();
        title = productTitle.get(0).select("h1.page-title").text();
        price = Optional.ofNullable(productTitle.get(0).select("span.price").text())
            .filter(s -> !s.isEmpty())
            .map(reply -> {
                var split = reply.split("\\D+");
                return Double.parseDouble(split[1] + "." + split[2]);
            }).orElse(0.0);
        log.debug("Title: {}, Price: {}", title, price);

        var productInfo = document.select("div[class = product data items]");
        if (productInfo != null && !productInfo.isEmpty()) {
            description = productInfo.get(0).getElementById("description").text();
            log.debug("Description : {} ", description);
            var additionalElement = productInfo.get(0).getElementById("additional");
            var extra = new StringBuilder();
            if (additionalElement != null) {
                var detailElements = additionalElement.select("tr");
                detailElements.forEach(element -> {
                    var th = element.select("th").text();
                    var td = element.select("td").text();
                    extra.append(th).append(" : ").append(td).append(" | ");
                });
                extra.delete(extra.lastIndexOf("|"), extra.lastIndexOf(" "));
                details = extra.toString();
                log.debug("Details: [{}]", extra);
            }
        }
        return
            Optional.ofNullable(Product.getBasicProduct(
                title == null ? null : Objects.requireNonNull(title),
                price == null ? 0.0 : Objects.requireNonNull(price),
                description == null ? null : Objects.requireNonNull(description),
                details == null ? null : Objects.requireNonNull(details)
            ));
    }

}
