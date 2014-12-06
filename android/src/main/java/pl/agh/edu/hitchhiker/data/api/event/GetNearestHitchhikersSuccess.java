package pl.agh.edu.hitchhiker.data.api.event;

import pl.agh.edu.hitchhiker.data.models.NearestHitchhikersResponse;

public class GetNearestHitchhikersSuccess {
    private final NearestHitchhikersResponse nearestHitchhikersResponse;

    public GetNearestHitchhikersSuccess(NearestHitchhikersResponse nearestHitchhikersResponse) {
        this.nearestHitchhikersResponse = nearestHitchhikersResponse;
    }

    public NearestHitchhikersResponse getNearestHitchhikersResponse() {
        return nearestHitchhikersResponse;
    }
}
