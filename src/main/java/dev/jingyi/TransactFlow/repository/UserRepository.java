package dev.jingyi.TransactFlow.repository;

import dev.jingyi.TransactFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// user CRUD


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

