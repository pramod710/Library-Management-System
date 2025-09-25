package com.library.repository;

import com.library.model.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    List<IssuedBook> findByReturnedFalse();
    List<IssuedBook> findByStudentId(String studentId);
}
