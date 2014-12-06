package pl.edu.agh.pp.hitchhiker.webservice.api;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

/**
 * Resource assembler for {@link Hitchhiker} class
 * @author patrykkurczyna
 *
 */
@Component
public final class HitchhikerResourceAssembler implements
	ResourceAssembler<Hitchhiker, Resource<Hitchhiker>> {
	
	/**
	 * Converts {@link Hitchhiker} entity to {@link Resource}
	 */
	public Resource<Hitchhiker> toResource(Hitchhiker entity) {
        try {
            Resource<Hitchhiker> advertResource = new Resource<Hitchhiker>(entity);
            return advertResource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}

