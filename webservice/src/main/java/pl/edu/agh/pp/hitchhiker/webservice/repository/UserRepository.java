package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.User;

@RestResource(path = "users", rel = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	List<User> findAll();
	
	@Query("SELECT u.deviceId FROM User u")	
	public List<String> findAllDevices();
	
//	public Page<Hitchhiker> findByLastname(@Param("lastname") String lastname, Pageable pageable);
	
//	public Page<Hitchhiker> findByFirstname(@Param("firstname") String firstname, Pageable pageable);
		
	public User findById(@Param("user_id") Integer id);
	
	public User findByLogin(@Param("login") String login);
	
	
	//public boolean matches(@Param("id") Integer id, @Param("login") String login, @Param("password") String password);
}
