package ru.tinkoff.seminars.accounting.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.seminars.accounting.model.UserModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {

     Optional<UserModel> findByLogin(String login);
     
}
