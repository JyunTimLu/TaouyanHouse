package tim.tayouanhouseprice;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import butterknife.BindView;
import tim.tayouanhouseprice.adapter.RecyclerViewAdapter;
import tim.tayouanhouseprice.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("lvr_land");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, dataSnapshot);
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

                Log.d("TAG", dataSnapshot.getChildrenCount() + "/" + s);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
