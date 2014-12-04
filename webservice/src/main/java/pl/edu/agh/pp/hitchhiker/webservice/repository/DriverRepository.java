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
	
	@Query("SELECT d.deviceId FROM Driver d WHERE acos(sin(radians(:latitude)) * sin(radians(d.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(d.geoLatitude)) * cos(radians(d.geoLongitude) - radians(:longitude))) * 6371 <= :radius")	
	public List<String> findDevicesInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude);
	
	@Query("SELECT count(*) from Driver d WHERE d.active = true AND d.user.id = :userId")
	public Long countActive(@Param("userId") Integer userId);
	
}
