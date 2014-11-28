package pl.agh.edu.hitchhiker.data.api.event;

import java.util.List;

import pl.agh.edu.hitchhiker.data.models.Hitchhiker;

public class GetHitchhikersSuccess {
    private List<Hitchhiker> hitchhikers;

    public GetHitchhikersSuccess() {
    }

    public GetHitchhikersSuccess(List<Hitchhiker> hitchhikers) {
        this.hitchhikers = hitchhikers;
    }

    public List<Hitchhiker> getHitchhikers() {
        return hitchhikers;
    }

    public void setHitchhikers(List<Hitchhiker> hitchhikers) {
        this.hitchhikers = hitchhikers;
    }
}
