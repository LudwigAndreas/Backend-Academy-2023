package ru.tinkoff.seminars.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.seminars.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT MAX(p.id) FROM Person p")
    Long findLargestId();

    @EntityGraph(attributePaths = {"children"})
    Optional<Person> findById(Long id);
}
