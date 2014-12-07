package pl.agh.edu.hitchhiker.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearestHitchhikersResponse {
    @SerializedName("content")
    private List<Hitchhiker> hitchhikers;

    public List<Hitchhiker> getHitchhikers() {
        return hitchhikers;
    }

    public void setHitchhikers(List<Hitchhiker> hitchhikers) {
        this.hitchhikers = hitchhikers;
    }
}
