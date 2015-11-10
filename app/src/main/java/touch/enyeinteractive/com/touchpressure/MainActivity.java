package touch.enyeinteractive.com.touchpressure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private TextView mTouchSpecs;
    private TouchViewGroup mTouchArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTouchSpecs = (TextView) findViewById(R.id.touch_details);
        mTouchArea = (TouchViewGroup) findViewById(R.id.touch_area);
    }
}
