package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;



import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 */

public interface GoudVinkjeUserRepository extends JpaRepository<GoudVinkjeUser, Long> {
    Optional<GoudVinkjeUser> findByUsername(String username);
}
