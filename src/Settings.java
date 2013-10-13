/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    private String cameraAlbum = "";
    private String dropboxAlbum = "";

    public Settings()
    {
    }

    public void setCameraAlbum(String cameraAlbum) {
        this.cameraAlbum = cameraAlbum;
    }
    public void setDropboxAlbum(String dropboxAlbum) {
        this.dropboxAlbum = dropboxAlbum;
    }
    public String getCameraAlbum() {
        return cameraAlbum;
    }

    public String getDropboxAlbum() {
        return dropboxAlbum;
    }
}
