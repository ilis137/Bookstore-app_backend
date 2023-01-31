package com.example.bookstorebackendappcfp.repository;

import com.example.bookstorebackendappcfp.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
