package com.example.simple_user_api.repository;

import com.example.simple_user_api.model.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
