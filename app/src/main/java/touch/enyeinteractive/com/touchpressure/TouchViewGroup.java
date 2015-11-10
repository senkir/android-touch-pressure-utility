package touch.enyeinteractive.com.touchpressure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TouchViewGroup extends View {
    private static final String TAG = TouchViewGroup.class.getSimpleName();
    private static final int[] COLOR_MAP = new int[] {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.GRAY
    };
    private Map<Integer, SmartPoint> mPoints = new HashMap<>();

    public TouchViewGroup(Context context) {
        super(context);
        init();
    }


    public TouchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public TouchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int count = event.getPointerCount();
                for (int i = 0; i < count; i++) {
                    parseEvent(event.getPointerId(i), event);
                }
                return true;
            }
        });
    }


    private void parseEvent(int pointerId, MotionEvent event) {
        SmartPoint p;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_MOVE:
                float size;
                try {
                    size = event.getSize(pointerId);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage(), e);
                    return;
                }
                /**
                 * Can determine size of pointer for callibration by tapping device on an icon
                 * palm detection can be determined by ignoring touch events considerably larger than
                 * this.
                 * eg: finger is size 0.023529
                 * palm is size 0.03529
                 */
                float pressure = event.getPressure(pointerId);
                p = generatePoint(pointerId, event);
                if (p == null) {
                    Log.e(TAG, "invalid index position " + pointerId);
                    return;
                }
                p.size = size;
                p.pressure = pressure;
                p.point.x = Math.round(event.getX(pointerId));
                p.point.y = Math.round(event.getY(pointerId));
                p.paint.setStrokeWidth(size * 1000.0f);
                Log.v(TAG, String.format("motion event at size %s pressure %s x %s y %s (total " +
                                "points %s)", size, pressure,
                        p.point.x, p.point.y, event.getPointerCount()));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "action UP points = " + event.getPointerCount());
                int index = event.getActionIndex();
                int count = event.getPointerCount();
                HashMap<Integer, SmartPoint> copy = new HashMap<>();
                for (int i = 0; i < count; i++) {
                    int id = event.getPointerId(i);
                    copy.put(id, mPoints.get(id));
                }
                //replace.  note this is probably really memory hungry
                mPoints = copy;
                mPoints.remove(index);
                invalidate();
                break;
            default:
                Log.w(TAG, "unknown touch event.  action = " + event.getAction());

        }
    }


    private SmartPoint generatePoint(int pointerId, MotionEvent event) {
        SmartPoint p;
        if (mPoints.size() < pointerId+1) {
            p = new SmartPoint();
            p.point = new Point();
            mPoints.put(pointerId, p);
            Paint paint = new Paint();
            paint.setColor(getColor(pointerId));
            p.paint = paint;
        } else {
            p = mPoints.get(pointerId);
        }
        return p;
    }


    private int getColor(int position) {
        if (position <= COLOR_MAP.length) {
            return COLOR_MAP[position];
        }
        return COLOR_MAP[COLOR_MAP.length -1];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoints.size() > 0) {
            for (Integer pointId : mPoints.keySet()) {
                SmartPoint point = mPoints.get(pointId);
                //draw crosshairs
                //vertical
                canvas.drawLine(point.point.x, 0.0f, point.point.x, getMeasuredHeight(), point
                        .paint);
                //horizontal
                canvas.drawLine(0.0f, point.point.y, getMeasuredWidth(), point.point.y, point
                        .paint);
                //draw circle
                canvas.drawCircle(point.point.x, point.point.y, point.size * 5000.0f, point.paint);
            }
        }
    }

    static class SmartPoint {
        Point point;
        Paint paint;
        float pressure;
        float size;
    }


}
