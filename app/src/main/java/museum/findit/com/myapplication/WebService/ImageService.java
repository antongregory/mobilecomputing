package museum.findit.com.myapplication.WebService;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by doniramadhan on 2016-10-19.
 */

public class ImageService {

    public static Task<Uri> getUrl(String imageName){
        StorageReference itemsReference = FirebaseStorage.getInstance().getReference().child("items");
        return itemsReference.child(imageName).getDownloadUrl();
    }
}
