package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.Driver;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;

/**
 * Spring Data REST Repository for {@link Driver} entities
 * @author patrykkurczyna
 *
 */
@RestResource(path = "drivers", rel = "drivers")
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long>{
	
	/**
	 * Finds device ids of all drivers
	 * @return list of all device ids of drivers
	 */
	@Query("SELECT d.deviceId FROM Driver d")	
	public List<String> findAllDevices();
	
	/**
	 * Finds device ids of drivers which are active in specified radius from given coordinates
	 * @param radius spread of looking
	 * @param latitude y coordinate
	 * @param longitude x coordinate
	 * @return list of driver's device ids
	 */
	@Query("SELECT d.deviceId FROM Driver d WHERE acos(sin(radians(:latitude)) * sin(radians(d.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(d.geoLatitude)) * cos(radians(d.geoLongitude) - radians(:longitude))) * 6371 <= :radius" +
			" and d.active = true")	
	public List<String> findActiveDevicesInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude);
	
	/**
	 * Finds all drivers which are active in specified radius from given coordinates
	 * @param radius spread of search
	 * @param latitude y coordinate
	 * @param longitude x coordinate
	 * @return list od drivers
	 */
	@Query("SELECT d FROM Driver d WHERE acos(sin(radians(:latitude)) * sin(radians(d.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(d.geoLatitude)) * cos(radians(d.geoLongitude) - radians(:longitude))) * 6371 <= :radius" +
			" and d.active = true")	
	public List<Driver> findActiveDriversInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude);
	
	/**
	 * Count number of active {@link Driver} drivers for specific {@link User}
	 * @param userId for this user we count
	 * @return number of active {@link Driver} drivers for specific {@link User}
	 */
	@Query("SELECT count(*) from Driver d WHERE d.active = true AND d.user.id = :userId")
	public Long countActive(@Param("userId") Integer userId);
	
	/**
	 * Finds driver by id
	 * @param id driver id
	 * @return {@link Driver}
	 */
	public Driver findById(@Param("id") Integer id);
	
}
