package app.finology.exception;

/**
 * Created by Ebrahim Kh.
 */

public class CrawlerBaseException extends RuntimeException {
    public CrawlerBaseException() {
        super();
    }

    public CrawlerBaseException(String message) {
        super(message);
    }

}
