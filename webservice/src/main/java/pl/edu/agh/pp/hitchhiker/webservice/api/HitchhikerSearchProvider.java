package pl.edu.agh.pp.hitchhiker.webservice.api;

import java.util.List;

import pl.edu.agh.pp.hitchhiker.webservice.model.Hitchhiker;

/**
 * Search provider for {@link Hitchhiker}
 * @author patrykkurczyna
 *
 */
public interface HitchhikerSearchProvider {
	
	/**
	 * Method used for check whether or not hitchhikers match given criteria
	 * @param hitchhikers list of {@link Hitchhiker} to confront with given criteria
	 * @param criteria {@link HitchhikerSearchCriteria} criteria for hitchhiker search
	 * @return list of hitchhikers which is filtered using given search criteria
	 */
	public abstract List<Hitchhiker> find(List<Hitchhiker> hitchhikers,
			HitchhikerSearchCriteria criteria);

}