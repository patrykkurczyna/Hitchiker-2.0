package pl.agh.edu.hitchhiker.data.api.event;

public class RegisterDriverFailure {
    private int status;

    public RegisterDriverFailure() {
    }

    public RegisterDriverFailure(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
