package com.cinema.security;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Modifying
    @Transactional
    @Query(value = "UPDATE users_roles SET role = :r,  username=:u WHERE user_id = :idu  AND role_id = :idr" ,nativeQuery = true)
    public void addUserRoles(@Param("idu") Long idU, @Param("idr") Long idr, @Param("u") String u, @Param("r") String r);

    @Query(value = "SELECT * FROM users_roles" ,nativeQuery = true)
    public List<users_roles> findUsersRoles();
    
    public class users_roles{
        private int id ;
        private String username;
        private String role;
        private Long userId;
        private Long roleId;
    }
}
