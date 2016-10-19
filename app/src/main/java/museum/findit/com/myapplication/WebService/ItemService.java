package museum.findit.com.myapplication.WebService;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import museum.findit.com.myapplication.model.ItemModel;

/**
 * Created by doniramadhan on 2016-10-18.
 */

public class ItemService {
    private static DatabaseReference gamesDatabase = FirebaseDatabase.getInstance().getReference("items");

    public static Task<ArrayList<ItemModel>> getAll(){
        final TaskCompletionSource<ArrayList<ItemModel>> taskCompletionSource = new TaskCompletionSource<>();
        gamesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<ItemModel>> objectListType = new GenericTypeIndicator<ArrayList<ItemModel>>() {};
                ArrayList<ItemModel> items = dataSnapshot.getValue(objectListType);
                taskCompletionSource.setResult(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                taskCompletionSource.setException(new Exception("items can't be retrieved"));
            }
        });

        return taskCompletionSource.getTask();
    }
}
