package pl.dudios.shopmvn.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.common.model.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

}
