/*
 * Copyright 2016 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.donghyun.myhack.MapActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.donghyun.myhack.ApplicationController;
import com.example.donghyun.myhack.CameraActivity.CameraActivity;
import com.example.donghyun.myhack.InfoActivity;
import com.example.donghyun.myhack.NetworkService;
import com.example.donghyun.myhack.OriInfo;
import com.example.donghyun.myhack.R;
import com.example.donghyun.myhack.SearchActivity.SearchActivity;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Sample class for map viewer library.
 *
 * @author kyjkim
 */
public class NMapViewer extends NMapActivity {
	private static final String LOG_TAG = "NMapViewer";
	private static final boolean DEBUG = false;

	// set your Client ID which is registered for NMapViewer library.
	private static final String CLIENT_ID = "5QlbWErrgs5Cl4XOuQyG";

	private MapContainerView mMapContainerView;

	private NMapView mMapView;
	private NMapController mMapController;


	private NMapOverlayManager mOverlayManager;
	private NMapMyLocationOverlay mMyLocationOverlay;
	private NMapLocationManager mMapLocationManager;
	private NMapCompassManager mMapCompassManager;

	private NMapViewerResourceProvider mMapViewerResourceProvider;

	public NMapPOIdata poiData;

	ApplicationController applicationController;
	NetworkService networkService;
	List<OriInfo> ories;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// create map view
		mMapView = new NMapView(this);

		// create parent view to rotate map view
		mMapContainerView = new MapContainerView(this);

		ImageView img = new ImageView(this);
		img.setImageResource(R.drawable.nextbtn);

		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*
				 *
				 * 잠시 이 버튼 좀 빌리겠습니다. 카메라 버튼 누르면 건물정보페이지로 넘어갑니다.
				 *
				*/

//				Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
//				startActivity(intent);


				Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
				intent.putParcelableArrayListExtra("ories", (ArrayList<? extends Parcelable>) ories);
				startActivity(intent);
			}
		});

		Button searchBtn = new Button(this);
		searchBtn.setText("건물 명, 편의시설 명, 장소 명");
		searchBtn.setTextColor(Color.LTGRAY);
		searchBtn.setBackgroundResource(R.drawable.edittext_round);
		searchBtn.setHeight(100);
		searchBtn.setTextSize(15);
		searchBtn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
				intent.putParcelableArrayListExtra("ories", (ArrayList<? extends Parcelable>) ories);
				startActivity(intent);
			}
		});



		FrameLayout preview = new FrameLayout(this);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.leftMargin =0;
		params.topMargin = 0;

		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params1.gravity = Gravity.RIGHT|Gravity.BOTTOM;
		params1.bottomMargin = 100;
		params1.rightMargin = 50;

		FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.CENTER|Gravity.TOP;
		params2.topMargin = 70;
		params2.leftMargin = 30;
		params2.rightMargin = 30;

		preview.addView(mMapView,params);
		preview.addView(img,params1);
		preview.addView(searchBtn,params2);


		mMapContainerView.addView(preview);

		// set the activity content to the parent view
		setContentView(mMapContainerView);

		// set a registered Client Id for Open MapViewer Library
		mMapView.setClientId(CLIENT_ID);

		// initialize map view
		mMapView.setClickable(true);
		mMapView.setEnabled(true);
		mMapView.setFocusable(true);
		mMapView.setFocusableInTouchMode(true);
		mMapView.requestFocus();

		// register listener for map state changes
		mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
		//mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);
		//mMapView.setOnMapViewDelegate(onMapViewTouchDelegate);

		// use map controller to zoom in/out, pan and set map center, zoom level etc.
		mMapController = mMapView.getMapController();

		// use built in zoom controls
		/*NMapView.LayoutParams lp = new NMapView.LayoutParams(NMapView.LayoutParams.WRAP_CONTENT,
			NMapView.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);
		mMapView.setBuiltInZoomControls(true, lp);*/

		// create resource provider
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

		// set data provider listener
		//super.setMapDataProviderListener(onDataProviderListener);

		// create overlay manager
		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
		// register callout overlay listener to customize it.
		//mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
		// register callout overlay view listener to customize it.
		//mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener);

		// location manager
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

		// compass manager
		mMapCompassManager = new NMapCompassManager(this);

		// create my location overlay
		mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);




	}

	/* Test Functions */
	private void startMyLocation() {

		if (mMyLocationOverlay != null) {
			if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
				mOverlayManager.addOverlay(mMyLocationOverlay);
			}

			if (mMapLocationManager.isMyLocationEnabled()) {

				if (!mMapView.isAutoRotateEnabled()) {
					mMyLocationOverlay.setCompassHeadingVisible(true);

					mMapCompassManager.enableCompass();


					mMapView.setAutoRotateEnabled(true, false);

					mMapContainerView.requestLayout();
				}

				mMapView.postInvalidate();



			} else {
				boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
				if (!isMyLocationEnabled) {
					Toast.makeText(NMapViewer.this, "Please enable a My Location source in system settings",
							Toast.LENGTH_LONG).show();

					Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(goToSettings);

					return;
				}
			}
		}

	}



	/* MyLocation Listener */
	private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

		@Override
		public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
			if (mMapController != null) {
				// mMapController.animateTo(myLocation);
				//Toast.makeText(getApplicationContext(),myLocation.getLatitude()+"/"+myLocation.getLongitude(),Toast.LENGTH_SHORT).show();

				getdata(myLocation.latitude,myLocation.longitude);
			}

			return true;
		}

		@Override
		public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

			Toast.makeText(NMapViewer.this, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

			Toast.makeText(NMapViewer.this, "Your current location is unavailable area.", Toast.LENGTH_LONG).show();

		}

	};

	/* MapView State Change Listener*/
	private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

		@Override
		public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {

			if (errorInfo == null) { // success
				// restore map view state such as map center position and zoom level.
//				restoreInstanceState();
				startMyLocation();


				mMapController.setMapCenter(new NGeoPoint(127.073890,37.550583), 19);
//				mMapController.setMapCenter(new NGeoPoint(127.044804,37.468642), 19);

				new MyAsyncTask(poiData).execute(mMapLocationManager);
				//getdata();

			} else { // fail
				Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

				Toast.makeText(NMapViewer.this, errorInfo.toString(), Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
			if (DEBUG) {
				Log.i(LOG_TAG, "onAnimationStateChange: animType=" + animType + ", animState=" + animState);
			}
		}

		@Override
		public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
			if (DEBUG) {
				Log.i(LOG_TAG, "onMapCenterChange: center=" + center.toString());
			}
		}

		@Override
		public void onZoomLevelChange(NMapView mapView, int level) {
			if (DEBUG) {
				Log.i(LOG_TAG, "onZoomLevelChange: level=" + level);
			}
		}

		@Override
		public void onMapCenterChangeFine(NMapView mapView) {

		}
	};



	/**
	 * Container view class to rotate map view.
	 */
	private class MapContainerView extends ViewGroup {

		public MapContainerView(Context context) {
			super(context);
		}

		@Override
		protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
			final int width = getWidth();
			final int height = getHeight();
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View view = getChildAt(i);
				final int childWidth = view.getMeasuredWidth();
				final int childHeight = view.getMeasuredHeight();
				final int childLeft = (width - childWidth) / 2;
				final int childTop = (height - childHeight) / 2;
				view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
			}

			if (changed) {
				mOverlayManager.onSizeChanged(width, height);
			}
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
			int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
			int sizeSpecWidth = widthMeasureSpec;
			int sizeSpecHeight = heightMeasureSpec;

			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View view = getChildAt(i);

				if (view instanceof NMapView) {
					if (mMapView.isAutoRotateEnabled()) {
						int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
						sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
						sizeSpecHeight = sizeSpecWidth;
					}
				}

				view.measure(sizeSpecWidth, sizeSpecHeight);
			}
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}




	public void getdata(double lat, double lon){
		applicationController = ApplicationController.getInstance();
		applicationController.buildNetworkService("35.166.255.30", 80);
		networkService = applicationController.getNetworkService();

		//final ProgressDialog dialog = ProgressDialog.show(this,"","현재위치를 찾고 있습니다.",true);

		Call<List<OriInfo>> DetailCall = networkService.getAllOries(lat, lon);
		DetailCall.enqueue(new Callback<List<OriInfo>>() {
			@Override
			public void onResponse(Response<List<OriInfo>> response, Retrofit retrofit) {
				if (response.isSuccess()) {
					//Gson gson = new Gson();
					//String jsonString = gson.toJson(response.body());
					//Log.i("MyTaggggggggg", jsonString);

					ories = response.body();

					if(ories == null){
						Log.i("에러","");
					}

					Log.i("오리 정보", ories.get(0).name + ", " + ories.get(0).facility);

					mOverlayManager.clearOverlays();
					poiData = new NMapPOIdata(ories.size(), mMapViewerResourceProvider);
					poiData.beginPOIdata(ories.size());
					for(int i=0;i<ories.size();i++){
						if(ories.get(i).near == 1) { // 미터단위
							poiData.addPOIitem(ories.get(i).lon, ories.get(i).lat, null, NMapPOIflagType.PIN, 0); //파랑
						}else{
							poiData.addPOIitem(ories.get(i).lon, ories.get(i).lat, null, NMapPOIflagType.SPOT, 0); //빨강
						}
					}
					poiData.endPOIdata();
					mOverlayManager.createPOIdataOverlay(poiData, null);


				} else {
					int statusCode = response.code();
					Log.i("MyTag", "에러 상태 코드 : " + statusCode);
				}
			}
			@Override
			public void onFailure(Throwable t) {
				//Log.i("MyTag", t.getMessage());
			}
		});
	}



	public class MyAsyncTask extends AsyncTask<NMapLocationManager, Void, NGeoPoint> {
		NMapPOIdata poiData;
		ProgressDialog dialog;

		public MyAsyncTask(NMapPOIdata poiData){
			this.poiData = poiData;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(NMapViewer.this,"","현재위치를 찾고 있습니다.",true);
			super.onPreExecute();
		}

		@Override
		protected NGeoPoint doInBackground(NMapLocationManager... params) {
			NGeoPoint cur;
			while((cur = params[0].getMyLocation()) == null);

			return cur;
		}

		@Override
		protected void onPostExecute(NGeoPoint result) {
			super.onPostExecute(result);

			getdata(result.getLatitude(),result.getLongitude());

			if(dialog.isShowing())
				dialog.dismiss();

		}


	}



}



