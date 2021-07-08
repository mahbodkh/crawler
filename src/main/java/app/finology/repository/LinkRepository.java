package app.finology.repository;

import app.finology.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Ebrahim Kh.
 */
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
}
