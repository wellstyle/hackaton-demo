package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JusticeRepository extends JpaRepository<Justice, Long> {

	@Query("select j.penalty as rate, count(j) as count "
			+ "from Judgement j where j.justice = ?1 group by j.penalty order by j.penalty ASC")
	List<Object[]> countByPenalty(Justice justice);
	
	@Query("select count(j) as count "
			+ "from Judgement j where j.justice = ?1")
	long totalCount(Justice justice);
	
    @Query("select sum(j.penalty)*1.0 / count(j) as averagePenalty "
            + "from Judgement j where j.justice = ?1")
    float averagePenalty(Justice justice);
    
   /* @Query("select sum(j.penalty) as sumPenalty "
    		   + "from Judgement j where j.justice = ?1")
    float sumPenalty(Justice justice);*/
    
    
    
    
}
