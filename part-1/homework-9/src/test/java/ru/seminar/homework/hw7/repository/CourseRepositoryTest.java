package ru.seminar.homework.hw7.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.seminar.homework.hw7.model.Course;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;



    @Test
    public void findByOrderByName_whenValidPage_thenCorrect() {
        // given
        List<Course> courses = List.of(
                new Course(null, "Java", "Java Course", 150, new ArrayList<>()),
                new Course(null, "Python", "Python Course", 150, new ArrayList<>()),
                new Course(null, "C++", "C++ Course", 150, new ArrayList<>())
        );
        entityManager.persist(courses.get(0));
        entityManager.persist(courses.get(1));
        entityManager.persist(courses.get(2));
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 3);
        // when

        Page<Course> found = courseRepository.findByOrderByName(pageable);
        // then
        assert(found.getContent().size() == 3);
        List<String> sortedCourses = found.getContent().stream().map(Course::getName).sorted().toList();
        assert (found.getContent().get(0).getName().equals(sortedCourses.get(0)));
        assert (found.getContent().get(1).getName().equals(sortedCourses.get(1)));
        assert (found.getContent().get(2).getName().equals(sortedCourses.get(2)));
    }

//    @Test
//    @Transactional
//    public void updateStudent_whenAsyncUpdate_thenThrowsOptimisticLockException() {
//        // given
//        final Course course = courseRepository.save(new Course(null, "Java", "Java Course", 150, new ArrayList<>()));
//        assertEquals(0, course.getVersion());
//
//    }



}
