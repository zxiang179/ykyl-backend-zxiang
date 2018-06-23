package edu.ecnu.ykyl.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.Grade;

@Repository
@Transactional
public interface GradeRepository extends JpaRepository<Grade, Long> {

	@Query(value = "select * from t_grade", nativeQuery = true)
	public List<Grade> findGrades();

}
