package com.example.twitter;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long>{

    Role findByRole(String role);


}
