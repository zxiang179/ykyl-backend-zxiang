package edu.ecnu.ykyl.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Person;

@Transactional
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query(value = "from Person where address=?1")
	List<Person> findByAddress(String addressName);

}
