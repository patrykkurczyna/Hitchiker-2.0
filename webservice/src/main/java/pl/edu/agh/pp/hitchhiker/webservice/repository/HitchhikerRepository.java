package pl.edu.agh.pp.hitchhiker.webservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

@RestResource(path = "hitchhikers", rel = "hitchhikers")
public interface HitchhikerRepository extends PagingAndSortingRepository<Hitchhiker, Integer> {
}
