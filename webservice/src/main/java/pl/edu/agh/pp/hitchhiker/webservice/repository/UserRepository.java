package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.User;

/**
 * Spring Data REST Repository for {@link User} entities
 * @author patrykkurczyna
 *
 */
@RestResource(path = "users", rel = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	/**
	 * Gets all users
	 */
	List<User> findAll();
	
	/**
	 * Finds user by id
	 * @param id user id
	 * @return {@link User}
	 */
	public User findById(@Param("user_id") Integer id);
	
	/**
	 * Finds user by login
	 * @param login user login
	 * @return {@link User}
	 */
	public User findByLogin(@Param("login") String login);
	
	/**
	 * Finds user by lastname
	 * @param lastname user lastname
	 * @return {@link User}
	 */
	public Page<User> findByLastname(@Param("lastname") String lastname, Pageable pageable);
	
	/**
	 * Finds user by firstname
	 * @param firstname user firstname
	 * @return {@link User}
	 */
	public Page<User> findByFirstname(@Param("firstname") String firstname, Pageable pageable);
	
	/**
	 * Finds user by firstname and lastname
	 * @param firstname user firstname
	 * @param lastname user lastname
	 * @return {@link User}
	 */
	public Page<User> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname, Pageable pageable);

}
