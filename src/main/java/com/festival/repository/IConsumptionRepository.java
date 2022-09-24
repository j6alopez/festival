package com.festival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.festival.model.Consumption;

public interface IConsumptionRepository extends JpaRepository<Consumption, Integer> {
	
	@Query(value = "SELECT * FROM consumption AS c "
				 + "WHERE c.id_dispenser = id_dispenser AND c.ended = false",nativeQuery = true)
	
	public Consumption consumptionOpened (@Param("id_dispenser") Integer idDispenser);
	
}
