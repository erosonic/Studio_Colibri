package shavaliev_dinar.studio_colibri;

public class PhotoObject {

    private String photoURL;
    private String creationDate;

    public PhotoObject(String photoURL, String creationDate) {
        this.photoURL = photoURL;
        this.creationDate = creationDate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
