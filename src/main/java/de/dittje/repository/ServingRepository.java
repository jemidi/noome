package de.dittje.repository;

import de.dittje.domain.Serving;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Serving entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServingRepository extends JpaRepository<Serving, Long> {
}
