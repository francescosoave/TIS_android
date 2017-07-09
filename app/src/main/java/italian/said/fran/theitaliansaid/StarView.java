package italian.said.fran.theitaliansaid;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

/**
 * Created by Fran on 06/07/2017.
 */

public class StarView extends AppCompatImageButton{
    
    public StarView(Context context) {
        super(context);
    }
    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public void toGrey(){ this.setImageResource(R.drawable.gstar); }
    public void toGold(){ this.setImageResource(R.drawable.star); }
    public void show(){
        this.setVisibility(VISIBLE);
    }
    public void hide(){
        this.setVisibility(INVISIBLE);
    }
    
}
