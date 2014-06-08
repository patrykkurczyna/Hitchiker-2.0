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
	
	@Query("SELECT h FROM Hitchhiker h WHERE acos(sin(radians(:latitude)) * sin(radians(h.geoLatitude)) + " +
			"cos(radians(:latitude)) * cos(radians(h.geoLatitude)) * cos(radians(h.geoLongitude) - radians(:longitude))) * 6371 <= :radius")	
	public Page<Hitchhiker> findInRadiusFrom(@Param("radius") Double radius, @Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable);
	
	public Page<Hitchhiker> findByGeoLatitudeAndGeoLongitude(@Param("geoLatitude") String geoLatitude, @Param("geoLongitude") String geoLongitude, Pageable pageable);
	
	public Page<Hitchhiker> findByLastname(@Param("lastname") String lastname, Pageable pageable);
	
	public Page<Hitchhiker> findByFirstname(@Param("firstname") String firstname, Pageable pageable);
	
	public Page<Hitchhiker> findByDestination(@Param("destination") String destination, Pageable pageable);
	
	public Page<Hitchhiker> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname, Pageable pageable);
	
	public Page<Hitchhiker> findById(@Param("id") Integer id, Pageable pageable);
	
	public Page<Hitchhiker> findByNumberOfPassengers(@Param("numberOfPassengers") Integer numberOfPassengers, Pageable pageable);
}
