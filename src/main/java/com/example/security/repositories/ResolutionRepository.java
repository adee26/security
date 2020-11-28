package com.example.security.repositories;

import com.example.security.entities.Resolution;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResolutionRepository extends CrudRepository<Resolution, Long> {
	List<Resolution> findByOwner(Long owner);

	Resolution findById(long id);

	@Modifying
	@Query("UPDATE Resolution SET text = :text WHERE id = :id")
	void revise(Long id, String text);

	@Modifying
	@Query("UPDATE Resolution SET completed = 1 WHERE id = :id")
	void complete(Long id);
}
