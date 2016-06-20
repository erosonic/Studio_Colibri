package shavaliev_dinar.studio_colibri;

import java.util.ArrayList;

public interface DownloadPhotoCallback {
    void photosDownloaded(ArrayList<String> photosURLsList);
    void photosAreLoading();
    void photosLoadingError();
}
