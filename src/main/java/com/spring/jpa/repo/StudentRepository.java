package com.spring.jpa.repo;

import com.spring.jpa.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    // Add a custom JPQL
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    // Keep JPA default query implementation.
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThan(String firstName, Integer age);

    // Add a custom JPQL
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEquals(String firstName, Integer age);

    // Add a native query
    @Query(value = "SELECT * FROM Student WHERE first_name = ?1 AND age >= ?2",nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqualsNative(String firstName, Integer age);

    //Add a native query with a named parameters
    @Query(value = "SELECT * FROM Student WHERE first_name = :firstName AND age >= :age",nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqualsNativeWithNamedParam(@Param("firstName") String firstName, @Param("age") Integer age);

    @Transactional
    @Modifying  // tells jpa that we aren't returning anything back from DB to our entity.. we just need to update our DB table.. usually use with Delete or Update.
    @Query(value = "DELETE FROM Student u WHERE u.id = ?1",nativeQuery = true)
    int CustomDeleteStudentById(Long id);
}
