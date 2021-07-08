package app.finology.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.beans.Transient;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Ebrahim Kh.
 */

@Entity
@Table(name = "\"link\"")
@NoArgsConstructor
@Data
@ToString
@Builder
public class Link {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "url", length = 100)
    private String url;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Builder(toBuilder = true)
    public Link(Long id, String url, Date created) {
        setId(id);
        setUrl(url);
        setCreated(created);
    }

    @Transient
    public static Link getBasicLink(String url) {
        return Link.builder()
            .url(url)
            .created(new Date())
            .build();
    }
}
