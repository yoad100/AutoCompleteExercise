package com.bottomline.exercise.auto.complete.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bottomline.exercise.auto.complete.model.Boy;

@Repository
public interface AutoCompleteRepository extends JpaRepository<Boy,Integer>{

}
