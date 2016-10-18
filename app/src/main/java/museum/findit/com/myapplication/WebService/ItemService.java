package museum.findit.com.myapplication.WebService;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by doniramadhan on 2016-10-18.
 */

public class ItemService {
    static DatabaseReference gamesDatabase = FirebaseDatabase.getInstance().getReference("items");

    public static Task<List<Object>> getAll(){
        final TaskCompletionSource<List<Object>> taskCompletionSource = new TaskCompletionSource<>();
        gamesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Object>> objectListType = new GenericTypeIndicator<List<Object>>() {};
                List<Object> items = dataSnapshot.getValue(objectListType);
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
