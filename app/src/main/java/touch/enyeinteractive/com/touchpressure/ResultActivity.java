package touch.enyeinteractive.com.touchpressure;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.List;

import touch.enyeinteractive.com.touchpressure.PairUtility.GenericDevice;
import touch.enyeinteractive.com.touchpressure.PairUtility.OnScanFinished;


public class ResultActivity extends AppCompatActivity {
    private static final String TAG = ResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_result);
        findViewById(R.id.action_scan).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void start() {
        PairUtility u = new PairUtility(this);
        u.scan(new OnScanFinished() {
            @Override
            public void onFinished(List<GenericDevice> devices) {
                Log.v(TAG, "onFinished");
                showResult(devices);
            }
        });
    }


    private void showResult(List<GenericDevice> devices) {

    }

    static class ResultAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }


        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
