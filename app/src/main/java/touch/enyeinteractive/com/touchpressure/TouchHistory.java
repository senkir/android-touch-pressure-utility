package touch.enyeinteractive.com.touchpressure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class TouchHistory {
    private static final String TAG = TouchHistory.class.getSimpleName();

    private Collection<Point> mPoints = new LinkedHashSet<>();
    Paint mPaint ;

    public TouchHistory(Paint paint) {
        mPaint = paint;
        mPaint.setStrokeWidth(10);
    }

    void add (Point point) {
        mPoints.add(point);
    }


    public void draw(Canvas canvas) {
        Log.v(TAG, "draw");
        Point last = null;
        for (Point point : mPoints) {
            if (last == null) {
                last = point;
                continue;
            }
            Log.v(TAG, String.format("from %s to %s", last, point));
            canvas.drawLine(last.x, last.y, point.x, point.y, mPaint);
            last = point;
        }
    }

    public void clear() {
        mPoints.clear();;
    }
}
