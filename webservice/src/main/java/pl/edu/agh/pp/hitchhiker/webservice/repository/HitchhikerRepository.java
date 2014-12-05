package pl.edu.agh.pp.hitchhiker.webservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

@RestResource(path = "hitchhikers", rel = "hitchhikers")
public interface HitchhikerRepository extends PagingAndSortingRepository<Hitchhiker, Long> {
	
	List<Hitchhiker> findAll();
	
	@Query("SELECT count(*) from Hitchhiker h WHERE h.active = true AND h.user.id = :userId")
	public Long countActive(@Param("userId") Integer userId);
	
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius")	
	public Page<Hitchhiker> findInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable);
	
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius" +
			" and h.active = true and h.finalDestination like :destination")	
	public Page<Hitchhiker> findActiveInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("destination") String destination, Pageable pageable);
	
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius" +
			" and h.active = true and h.finalDestination like :destination")	
	public List<Hitchhiker> findActiveInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("destination") String destination);
	
	public Page<Hitchhiker> findByGeoLatitudeAndGeoLongitude(@Param("geoLatitude") String geoLatitude, @Param("geoLongitude") String geoLongitude, Pageable pageable);
	
	public Hitchhiker findById(@Param("id") Integer id);
	
	public Page<Hitchhiker> findByFinalDestination(@Param("finalDestination") String finalDestination, Pageable pageable);
	
	public Page<Hitchhiker> findByNumberOfPassengers(@Param("numberOfPassengers") Integer numberOfPassengers, Pageable pageable);
}
