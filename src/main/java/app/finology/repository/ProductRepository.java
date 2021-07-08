package app.finology.repository;

import app.finology.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ebrahim Kh.
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
