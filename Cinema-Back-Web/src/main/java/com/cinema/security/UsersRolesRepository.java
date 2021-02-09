package com.cinema.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRolesRepository extends JpaRepository<Role, Long>{
    
}
