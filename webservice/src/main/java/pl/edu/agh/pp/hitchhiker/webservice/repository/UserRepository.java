package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.User;

@RestResource(path = "users", rel = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	List<User> findAll();
		
	public User findById(@Param("user_id") Integer id);
	
	public User findByLogin(@Param("login") String login);
	
	public Page<User> findByLastname(@Param("lastname") String lastname, Pageable pageable);
	
	public Page<User> findByFirstname(@Param("firstname") String firstname, Pageable pageable);
	
	public Page<User> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname, Pageable pageable);

}
