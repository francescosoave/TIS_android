package italian.said.fran.theitaliansaid;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    TextView tvIta, tvEng, tvMain, tvTitle;
    ToggleButton btnInfo;
    Typeface fontReg, fontIta, fontBold;
    StarView btnAddFav, btnCheckFav;
    DatabaseHandler db;
    int id;
    String[] dataIta, dataEng;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        db = new DatabaseHandler(this);
        
        dataIta = cursorToArray(db.getData("Ita"));
        dataEng = cursorToArray(db.getData("Eng"));
        
        btnAddFav = (StarView) findViewById(R.id.btnAddFav);
        btnCheckFav = (StarView) findViewById(R.id.btnCheckFav);
        
        fontReg = Typeface.createFromAsset(getAssets(), "fonts/ZillaSlab-Regular.ttf");
        fontIta = Typeface.createFromAsset(getAssets(), "fonts/ZillaSlab-Italic.ttf");
        fontBold = Typeface.createFromAsset(getAssets(), "fonts/ZillaSlab-Bold.ttf");

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(fontBold);
        
        btnInfo = (ToggleButton) findViewById(R.id.btnInfo);
        
        tvIta = (TextView) findViewById(R.id.tvIta);
        tvIta.setTypeface(fontIta);
        
        tvEng = (TextView) findViewById(R.id.tvEng);
        tvEng.setTypeface(fontReg);
        
        tvMain = (TextView) findViewById(R.id.tvMain);
    
        btnInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvMain.setVisibility(View.INVISIBLE);
                    btnAddFav.setVisibility(View.INVISIBLE);
                    showInfo();
                } else {
                    tvMain.setVisibility(View.VISIBLE);
                    btnAddFav.setVisibility(View.VISIBLE);
                    changeSays();
                }
            }
        });
    
        tvMain.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeRight() {changeSays(); }
            @Override
            public void onSwipeLeft() { changeSays(); }
        });
    
        changeSays();
    }
    
    private String[] cursorToArray(Cursor cursor){
            ArrayList<String> listData = new ArrayList<>();
            while (cursor.moveToNext())
                listData.add(cursor.getString(0));
        
            String [] data = new String[listData.size()];
            return listData.toArray(data);
    }

    private void setToFav(){
        btnCheckFav.toGold();
        btnAddFav.hide();
    }
    
    private void setToDef(){
        btnCheckFav.toGrey();
        btnAddFav.show();
    }
    
    private void changeSays(){
        Random r = new Random();
        int n = r.nextInt(dataEng.length);
        this.id = n;
        
        if(db.dbCheckIfFav(this.id)) setToFav();
        else setToDef();
        
        setTextToTextView(n);
    }
    
    private void setTextToTextView(int val) {
        tvIta.setTypeface(fontIta);
        tvIta.setText(String.valueOf("\"" + dataIta[val] + ".\""));
        tvEng.setText(String.valueOf("\"" + dataEng[val] + ".\""));
    }

    private void showInfo(){
        tvIta.setTextSize(24f);
        tvIta.setTypeface(fontReg);
        tvEng.setTextSize(24f);
        tvIta.setText(getString(R.string.tvInfoIt));
        tvEng.setText(getString(R.string.tvInfoEn));
    }
    
    public void clickFav(View v){
        if(db.dbCheckIfFav(this.id)){
            setToDef();
            db.remFav(this.id);
        }else
            openFav();
    }
    
    public void btnAddFav(View v){
        btnAddFav.hide();
        btnCheckFav.toGold();
        db.addFav(this.id, this.tvIta.getText().toString(), this.tvEng.getText().toString());
    }
    
    public void openFav(){
        Intent fav = new Intent(MainActivity.this, FavActivity.class);
        startActivity(fav);
    }
    
}
