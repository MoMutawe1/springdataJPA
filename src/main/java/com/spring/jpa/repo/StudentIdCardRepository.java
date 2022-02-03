package com.spring.jpa.repo;

import com.spring.jpa.entity.StudentIdCard;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepository extends PagingAndSortingRepository<StudentIdCard, Long> {

}
