package com.framework.iginger.map;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LongPressOverlay extends Overlay implements OnDoubleTapListener,OnGestureListener{

	private final MapView mMapView;
	private final MapController mMapCtrl;
	private final GestureDetector gestureScanner = new GestureDetector(this);
	private int level = 0;
	
    public interface LongPressOverlayListener {
        /**
         * 
         */
        void onLongPress(GeoPoint geoPoint);
    }

    private final LongPressOverlayListener mListener;

    public LongPressOverlay(MapView mapView, MapController mapCtrl,
            LongPressOverlayListener listener) {
		mMapView = mapView;
		mMapCtrl = mapCtrl;
        mListener = listener;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		return gestureScanner.onTouchEvent(event);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		if(++level % 3 == 0){
			mMapCtrl.zoomIn();
			level = 0;
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
        GeoPoint geoPoint = mMapView.getProjection().fromPixels((int) e.getX(),
				(int) e.getY());
        mListener.onLongPress(geoPoint);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

}
