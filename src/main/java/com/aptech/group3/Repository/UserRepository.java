package com.aptech.group3.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.User;

import jakarta.transaction.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> findByCode(String code, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.role <> 'ADMIN'")
	List<User> findAllExceptAdmin();
	
	//field, mã số, status

	
	@Transactional
	@Modifying
	 @Query("update User u SET u.status = :status where u.id in :id ")
	    public void updateStatusStudent(UserStatus status, List<Long> id);

	 @Query("SELECT u FROM User u LEFT JOIN u.fields f " +
	           "WHERE (:status IS NULL OR u.status = :status) AND (:fieldId IS NULL OR f.id = :fieldId)")
	    List<User> findByFieldIdAndSubjectIdAndStatusAndCode(@Param("status") UserStatus status, @Param("fieldId") Long fieldId);

//		@Query("SELECT u FROM User u JOIN u.fields f " +
//		           "WHERE (:status IS NULL OR u.status = :status) " +
//		           "AND (:fieldId IS NULL OR f.id = :fieldId) " +
//		           "AND (:code IS NULL OR u.code LIKE %:code%)")
		//List<User> searchUserByCode()
	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Transactional
	@Modifying
	 @Query("update User u SET u.mobileCode = :mobileCode where u.id = :id ")
	    public void updateMobiCode(String mobileCode, Long id);
	
	 @Query("SELECT u FROM User u JOIN u.fields f WHERE u.status = :status AND f.id = :fieldId AND u.role LIKE :role")
	    List<User> findStudentByStatusAndField( UserStatus status,  Long fieldId, String role);
	 
	 @Query("SELECT u FROM User u WHERE u.status =:status AND  u.role LIKE :role ")
	 List<User> getListUserByRoleAndStatus(UserStatus status,  String role);
	//new
	 
    Optional<User> findById(Long id);
    List<User> findByRole(String role);
    Page<User> findByRoleNot(String role, Pageable pageable); 
    //User findByEmail(String email);
    public User findByResetPasswordToken(String token);	
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByUsername(@Param("email") String email);
    
    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email); 
    public Page<User> findByRole(String role, Pageable pageable); 
    int countStdByRole(String role);
    int countTcByRole(String role);
    int countEmByRole(String role);
    
    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    public void updatePassword(String password, Long id);

    public List<User> findByCode(String code);
    
}