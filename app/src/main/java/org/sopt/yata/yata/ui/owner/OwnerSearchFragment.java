package org.sopt.yata.yata.ui.owner;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.ui.common.DialogInsurance;
import org.sopt.yata.yata.ui.common.DialogMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static org.sopt.yata.yata.R.id.map;


/**
 * Created by SeungKoo on 2017. 6. 12..
 */

public class OwnerSearchFragment extends Fragment implements View.OnClickListener, TextWatcher {
    OwnerInfo ownerInfo;
    //기본
    AutoCompleteTextView topEdt;
    AutoCompleteTextView bottomEdt;
    RelativeLayout searchBtn;

    String start_location;
    String end_location;
    String token;
    int type;

    String slat;
    String slon;
    String elat;
    String elon;

    String s_reverse_addr;
    String e_reverse_addr;
    String s_full_addr;
    String e_full_addr;


    ArrayList<String> arrList =  new ArrayList<>();
    Set<String> setList = new HashSet<String>();

    //다이얼로그
    EditText edit_msg;
    Button message_close_btn;
    Button plus_btn;
    Button minus_btn;

    String time;
    int hour;
    int min;
    int companion;
    String msg;
    int dialog_companion;
    //보험가입 다이얼로그
    Button insurance_btn;

    NetworkService networkService;

    Context mContext;

    Button dialog_registerBtn;
    TimePicker timePicker;
    TextView textView_companion;

    public Activity activity;

    private GoogleMap gMap;
    SupportMapFragment mapFragment;

    public OwnerSearchFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        View layout = inflater.inflate(R.layout.fragment_owner_register, container, false);

        SharedPreferences user_type = mContext.getSharedPreferences("user_type", MODE_PRIVATE);
        type = user_type.getInt("user_type", 0);

        if(type == 1){
            final DialogRegistered dialogRegistered = new DialogRegistered(mContext);
            dialogRegistered.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    dialogRegistered.dismiss();
                }
            }, 5000);
        }

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topEdt = (AutoCompleteTextView) view.findViewById(R.id.topEdt);
        bottomEdt = (AutoCompleteTextView) view.findViewById(R.id.bottomEdt);
        searchBtn = (RelativeLayout) view.findViewById(R.id.searchbtn);

        SharedPreferences user_token = activity.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);

        searchBtn.setOnClickListener(this);



        //자동완성 코드 나중으로 미루기
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                Location location = new Location("");
//                location.setLatitude(place.getLatLng().latitude);
//                location.setLongitude(place.getLatLng().longitude);
//
//                topEdt.setText(start_location);
//            }
//
//            @Override
//            public void onError(Status status) {
//                Log.d("taehyungBB", "onError: PlaceAutocompleteFragment :" + status);
//            }
//        });

//        topEdt.setOnClickListener(this);
//        arrAdapt=new ArrayAdapter<String>(mContext, R.layout.start_list, items);
//        topEdt.setAdapter(arrAdapt);
//
//        SharedPreferences user_latest = activity.getSharedPreferences("latest_location", MODE_PRIVATE);
//        setList = user_latest.getStringSet("latest_location", null);
//        Log.d("taehyungBB", "onViewCreated: setList : " + setList);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchbtn:
                start_location = topEdt.getText().toString();
                end_location = bottomEdt.getText().toString();

                try{
                    Geocoder geocoder = new Geocoder(mContext);
                    List<Address> sAddress;
                    List<Address> eAddress;

                    sAddress = geocoder.getFromLocationName(start_location, 1);
                    eAddress = geocoder.getFromLocationName(end_location, 1);

                    slat = String.valueOf(sAddress.get(0).getLatitude());
                    slon = String.valueOf(sAddress.get(0).getLongitude());
                    elat = String.valueOf(eAddress.get(0).getLatitude());
                    elon = String.valueOf(eAddress.get(0).getLongitude());

//                    arrList.add(start_location);
//                    setList.add(start_location);

                    //최근 검색
//                    SharedPreferences l_loc = activity.getSharedPreferences("latest_location", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = l_loc.edit();
//
//                    editor.putStringSet("latest_location", setList);
//                    editor.commit();
//
//                    Log.d("taehyungBB", "onClick: arrList : " + arrList.get(0));

                }catch(Exception e){
                    Log.d("taehyungBB", "예외 발생 onClick: slat / slon:" + slat +" / " + slon);
                }

                ownerInfo = new OwnerInfo();
                ownerInfo.slat = slat;
                ownerInfo.slng = slon;
                ownerInfo.elat = elat;
                ownerInfo.elng = elon;
                Log.d("taehyungBB", "onClick: slat: " + slat);
                Log.d("taehyungBB", "onClick: slon: " + slon);
                Log.d("taehyungBB", "onClick: elat: " + elat);
                Log.d("taehyungBB", "onClick: elot: " + elon);

                try {
                    s_reverse_addr = reverseGeocoding_si(Double.parseDouble(slat), Double.parseDouble(slon), mContext);
                    e_reverse_addr = reverseGeocoding_si(Double.parseDouble(elat), Double.parseDouble(elon), mContext);
                    Log.d("taehyungBB", "onClick: reverseAddr SS: " + s_reverse_addr);
                    Log.d("taehyungBB", "onClick: reverseAddr EE: " + e_reverse_addr);

                    s_full_addr = reverseGeocoding_full(Double.parseDouble(slat), Double.parseDouble(slon), mContext);
                    e_full_addr = reverseGeocoding_full(Double.parseDouble(elat), Double.parseDouble(elon), mContext);
                    Log.d("taehyungBB", "onClick: reverseAddr SS: " + s_full_addr);
                    Log.d("taehyungBB", "onClick: reverseAddr EE: " + e_full_addr);

                    ownerInfo.saddr = s_full_addr;
                    ownerInfo.eaddr = e_full_addr;

                    ownerInfo.sloc = s_reverse_addr;
                    Log.d("taehyungBB", "onViewCreated: sloc: " + ownerInfo.sloc);
                    ownerInfo.eloc = e_reverse_addr;
                    Log.d("taehyungBB", "onViewCreated: eloc: " + ownerInfo.eloc);

                    Log.d("taehyungAA", "onClick in OwnerSearchFragment: " + mContext);
                    final DialogMessage dialog = new DialogMessage(mContext);
                    dialog.show();

                    dialog_registerBtn = (Button) dialog.findViewById(R.id.dialog_registerBtn);
                    timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                    textView_companion = (TextView) dialog.findViewById(R.id.textView_companion);
                    edit_msg = (EditText) dialog.findViewById(R.id.edit_msg);
                    message_close_btn = (Button) dialog.findViewById(R.id.message_close_btn);
                    plus_btn = (Button) dialog.findViewById(R.id.plus_btn);
                    minus_btn = (Button) dialog.findViewById(R.id.minus_btn);
                    dialog_companion = 0;

                    plus_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String temp;
                            if(dialog_companion < 6) {
                                dialog_companion += 1;
                                temp = String.valueOf(dialog_companion);
                                textView_companion.setText(temp);
                            }

                        }
                    });

                    minus_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(dialog_companion > 0) {
                                dialog_companion -= 1;
                                String temp = String.valueOf(dialog_companion);
                                textView_companion.setText(temp);
                            }
                        }
                    });

                    message_close_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog_registerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hour = timePicker.getCurrentHour();
                            min = timePicker.getCurrentMinute();
                            if(min < 10){
                                time = "" + hour + ":0" + min;
                            }else
                                time = "" + hour + ":" + min;

                            companion = Integer.parseInt(textView_companion.getText().toString());
                            msg = edit_msg.getText().toString();

                            ownerInfo.time = time;
                            ownerInfo.message = msg;
                            ownerInfo.companion = companion;
                            ownerInfo.token = token;

                            final DialogInsurance dialog_insurance = new DialogInsurance(mContext);
                            dialog.dismiss();
                            dialog_insurance.show();

                            insurance_btn = (Button) dialog_insurance.findViewById(R.id.insurance_btn);

                            insurance_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    networkService = ApplicationController.getInstance().getNetworkService();
                                    Log.d("taehyungBB", "onClick: networkService: " + networkService);

                                    Call<OwnerResult> requestOwnerRegister = networkService.getOwnerResult(ownerInfo, ownerInfo.token);
                                    requestOwnerRegister.enqueue(new Callback<OwnerResult>() {
                                        @Override
                                        public void onResponse(Call<OwnerResult> call, Response<OwnerResult> response) {
                                            Log.d("taehyungBB", "OwnerRegister response.isSuccessful(): " + response.isSuccessful());
                                            if (response.isSuccessful()) {
                                                Log.d("taehyungBB", "OwnerRegister response.body().message: " + response.body().message);
                                                try {
                                                    if (response.body().message.equals("success")) {
                                                        //성공시 로딩페이지 잠시 보여주면됨
                                                        Toast.makeText(mContext, "response 받음", Toast.LENGTH_SHORT).show();
                                                    }
                                                }catch(NullPointerException ne){
                                                    ne.printStackTrace();
                                                }
                                            }else {
                                                Toast.makeText(mContext, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<OwnerResult> call, Throwable t) {
                                            Toast.makeText(mContext, "Fail !", Toast.LENGTH_SHORT).show();

                                            Log.i("err", "" + t.getMessage());
                                        }
                                    });

                                    dialog_insurance.dismiss();

                                    FragmentManager fm = getChildFragmentManager();

                                    mapFragment = (SupportMapFragment) fm.findFragmentById(map);

                                    mapFragment = SupportMapFragment.newInstance();

                                    LatLng start_latlng = new LatLng(Double.parseDouble(slat), Double.parseDouble(slon));
                                    LatLng end_latlng = new LatLng(Double.parseDouble(elat), Double.parseDouble(elon));
                                    MarkerOptions startMarker = new MarkerOptions();
                                    startMarker.position(start_latlng)
                                            .title("출발지")
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_and_flags));
                                    gMap.addMarker(startMarker);

                                    MarkerOptions endMarker = new MarkerOptions();
                                    endMarker.position(end_latlng)
                                            .title("목적지");
                                    gMap.addMarker(endMarker);

                                    gMap.moveCamera(CameraUpdateFactory.newLatLng(start_latlng));
                                    gMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                                }
                            });


                            Log.d("taehyungBB", "onClick: time :" + time);
                            Log.d("taehyungBB", "onClick: companion :" + companion);
                            Log.d("taehyungBB", "onClick: msg :" + msg);
                            Log.d("taehyungBB", "onClick: token :" + token);


//                        dialog.dismiss();
                        }
                    });
                }catch(NullPointerException ne){
                    ne.printStackTrace();
                    Toast.makeText(mContext, "주소가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();

        mapFragment = (SupportMapFragment) fm.findFragmentById(map);

        mapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(map, mapFragment).commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                gMap = map;
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                try {
                    LatLng start_latlng = new LatLng(Double.parseDouble(slat), Double.parseDouble(slon));
                    LatLng end_latlng = new LatLng(Double.parseDouble(elat), Double.parseDouble(elon));
                    MarkerOptions startMarker = new MarkerOptions();
                    startMarker.position(start_latlng)
                            .title("출발지");
                    map.addMarker(startMarker);
                    MarkerOptions endMarker = new MarkerOptions();
                    endMarker.position(end_latlng)
                            .title("목적지");
                    map.addMarker(endMarker);

                    map.moveCamera(CameraUpdateFactory.newLatLng(start_latlng));
                    map.animateCamera(CameraUpdateFactory.zoomTo(10));
                }catch(NullPointerException ne){
                    LatLng default_latlng = new LatLng(37.56, 126.97);
                    map.moveCamera(CameraUpdateFactory.newLatLng(default_latlng));
                    map.animateCamera(CameraUpdateFactory.zoomTo(10));
                }
            }
        });
    }

    public String reverseGeocoding_si(double lat, double longi, Context context){
        String addressString = "No address found";
        try {
            Geocoder gc = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gc.getFromLocation(lat,longi, 1);
            StringBuilder sb = new StringBuilder();

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                    sb.append(address.getAddressLine(i)).append("");

                //  sb.append(address.getCountryName()).append("");
                // sb.append(address.getPostalCode()).append("");
                sb.append(address.getLocality()).append(" ");//시
//                sb.append(address.getThoroughfare()).append(" ");//동
//                sb.append(address.getFeatureName()).append(" ");//번지
            }
            addressString = sb.toString();
            Log.d("geocoding",addressString);
        } catch (Exception e){
            Log.e("geocoding", e.toString());
        }
        return addressString;
    }

    public String reverseGeocoding_full(double lat, double longi, Context context){
        String addressString = "No address found";
        try {
            Geocoder gc = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gc.getFromLocation(lat,longi, 1);
            StringBuilder sb = new StringBuilder();

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                    sb.append(address.getAddressLine(i)).append("");

                //  sb.append(address.getCountryName()).append("");
                sb.append(address.getAdminArea()).append(" ");//시
                sb.append(address.getLocality()).append(" ");//구
                sb.append(address.getThoroughfare()).append(" ");//동
                sb.append(address.getFeatureName()).append(" ");//번지
            }
            addressString = sb.toString();
            Log.d("geocoding",addressString);
        } catch (Exception e){
            Log.e("geocoding", e.toString());
        }
        return addressString;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}