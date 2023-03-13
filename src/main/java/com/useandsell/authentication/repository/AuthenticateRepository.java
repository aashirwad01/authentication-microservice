package com.useandsell.authentication.repository;

import com.useandsell.authentication.dto.Authenticate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthenticateRepository extends JpaRepository<Authenticate, Long> {
    @Query("SELECT c FROM Authenticate c WHERE c.email = ?1")
    Optional<Authenticate> findUserByEmail(String email);

    @Query("SELECT c FROM Authenticate c WHERE c.email = ?1")
    Authenticate findUserCredentialsByEmail(String email);

    @Query("SELECT c.isSeller FROM Authenticate c WHERE c.email=?1")
    Boolean findIfUserIsSeller(String email);

    @Query("SELECT c.isLoggedIn FROM Authenticate c WHERE c.email=?1")
    Boolean findIfUserIsLoggedIn(String email);


    @Transactional
    @Modifying
    @Query("UPDATE Authenticate p set p.isLoggedIn = false where p.email=:email")
    void logoutUser(@Param("email") String email

    );
}
