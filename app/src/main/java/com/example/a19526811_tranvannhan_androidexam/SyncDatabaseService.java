package com.example.a19526811_tranvannhan_androidexam;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.a19526811_tranvannhan_androidexam.dao.TaskDAO;
import com.example.a19526811_tranvannhan_androidexam.database.SqlDatabase;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncDatabaseService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.a19526811_tranvannhan_androidexam.action.FOO";
    private static final String ACTION_BAZ = "com.example.a19526811_tranvannhan_androidexam.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.a19526811_tranvannhan_androidexam.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.a19526811_tranvannhan_androidexam.extra.PARAM2";
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public SyncDatabaseService() {
        super("SyncDatabaseService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SqlDatabase sqlDatabase = Room.databaseBuilder(this, SqlDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        TaskDAO taskDAO = sqlDatabase.getTaskDAO();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Task task = snapshot.getValue(Task.class);
                if(taskDAO.checkExits(task.getId()) == false)
                    taskDAO.insert(task);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}