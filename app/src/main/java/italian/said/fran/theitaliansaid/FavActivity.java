package italian.said.fran.theitaliansaid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {
    private ListView mListView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        
        mListView = (ListView) findViewById(R.id.listView);
        DatabaseHandler db = new DatabaseHandler(FavActivity.this);
        showFav(db.getFav());
    }
    
    private void showFav(Cursor data) {
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext())
            listData.add(data.getString(1));
        
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
    
    public void closeFav() { FavActivity.this.finish(); }
}
