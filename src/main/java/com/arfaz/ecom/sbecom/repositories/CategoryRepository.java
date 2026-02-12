package com.arfaz.ecom.sbecom.repositories;


import com.arfaz.ecom.sbecom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
// This is a Spring Data JPA repository interface.
// Interfaces extending JpaRepository are used by Spring to
// generate the implementation automatically at runtime.
// No need to write boilerplate CRUD code.
//
// Type parameters:
//
//   <Category, Long>
//
// - Category(present in model package): the entity class this repository manages.
// - Long: the type of the primary key (ID) field in Category(present in model package).
//
// Spring will provide methods like:
// - save(), findById(), findAll(), deleteById(), etc.
//
// Interview line:
// "Extending JpaRepository gives us built-in CRUD operations and
// the ability to define query methods without writing implementation."
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
