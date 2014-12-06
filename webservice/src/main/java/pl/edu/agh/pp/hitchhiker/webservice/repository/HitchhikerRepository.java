package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;
import pl.edu.agh.pp.hitchhiker.webservice.model.User;

/**
 * Spring Data REST Repository for {@link Hitchhiker} entities
 * @author patrykkurczyna
 *
 */
@RestResource(path = "hitchhikers", rel = "hitchhikers")
public interface HitchhikerRepository extends PagingAndSortingRepository<Hitchhiker, Long> {
	
	/**
	 * Gets all hitchhikers
	 */
	List<Hitchhiker> findAll();
	
	/**
	 * Count number of active {@link Hitchhiker} hitchhikers for specific {@link User}
	 * @param userId for this user we count
	 * @return number of active {@link Hitchhiker} hitchhikers for specific {@link User}
	 */
	@Query("SELECT count(*) from Hitchhiker h WHERE h.active = true AND h.user.id = :userId")
	public Long countActive(@Param("userId") Integer userId);
	
	/**
	 * Finds {@link Hitchhiker} hitchhikers in specified radius from given coordinates
	 * @param radius spread of looking
	 * @param latitude y coordinate
	 * @param longitude x coordinate
	 * @param pageable indicates that results are pageable
	 * @return
	 */
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius")	
	public Page<Hitchhiker> findInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable);
	
	/**
	 * Finds {@link Hitchhiker} hitchhikers WHICH ARE ACTIVE in specified radius from given coordinates
	 * @param radius spread of looking
	 * @param latitude y coordinate
	 * @param longitude x coordinate
	 * @param pageable indicates that results are pageable
	 * @return
	 */
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius" +
			" and h.active = true")	
	public List<Hitchhiker> findActiveInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude);
	
	/**
	 * Finds hitchhiker by id
	 * @param id hitchhiker id
	 * @return {@link Hitchhiker}
	 */
	public Hitchhiker findById(@Param("id") Integer id);
}
