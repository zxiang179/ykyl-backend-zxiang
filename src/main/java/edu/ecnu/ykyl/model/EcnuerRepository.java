package edu.ecnu.ykyl.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Ecnuer;
import edu.ecnu.ykyl.entity.Unit;

@Repository
public interface EcnuerRepository extends JpaRepository<Ecnuer, Long> {

	@Query(value = "select unit from Ecnuer where ecnuer_name=?1")
	Unit findUnitByName(String name);

}
