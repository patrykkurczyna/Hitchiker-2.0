package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;

@RestResource(path = "drivers", rel = "drivers")
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long>{
	
	@Query("SELECT d.deviceId FROM Driver d")	
	public List<String> findAllDevices();
	
	@Query("SELECT count(*) from Driver d WHERE d.active = true AND d.user.id = :userId")
	public Long countActive(@Param("userId") Integer userId);
	
//	@Query("SELECT h from Hitchhiker h WHERE" + 
//			"")
//	public List<Hitchhiker> getHitchhikers(@Param("id") Integer id);
	
}
