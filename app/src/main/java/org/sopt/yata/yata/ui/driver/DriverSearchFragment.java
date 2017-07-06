package org.sopt.yata.yata.ui.driver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.sopt.yata.yata.R;
import org.sopt.yata.yata.application.ApplicationController;
import org.sopt.yata.yata.network.NetworkService;
import org.sopt.yata.yata.ui.common.DialogMiniDetail;
import org.sopt.yata.yata.vo.MatchingData;
import org.sopt.yata.yata.vo.OwnerLocationListVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by SeungKoo on 2017. 6. 12..
 */

public class DriverSearchFragment extends Fragment {
    ArrayAdapter<CharSequence> adspin1, adspin2, adspin3, adspin4;
    String start_choice_do ="";
    String start_choice_se ="";
    String end_choice_do="";
    String end_choice_se="";


    RelativeLayout searchBtn;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    DriverSearchListFragment driverSearchList;
    FragmentManager myFragmentManager;
    FrameLayout layout_map;
    ListView layout_list;
    Button list_btn;
    Button map_btn;
    Button switch_btn;

    Context mContext;
    Context listContext;

    NetworkService networkService;

    String token;

    private GoogleMap gMap;
    SupportMapFragment mapFragment;

    private ArrayList<MatchingData> driverListData;
    private Driver_RecyclerAdapter mAdapter;



    public Activity activity;
    public DriverSearchFragment(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        Log.d("ddddz", "onCreateView: container: "+ container);
        View v =  inflater.inflate(R.layout.fragment_driver_search, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner spin1 = (Spinner) view.findViewById(R.id.start_sido);
        final Spinner spin2 = (Spinner)view.findViewById(R.id.start_sigungu);
        final Spinner spin3 = (Spinner)view.findViewById(R.id.end_sido);
        final Spinner spin4 = (Spinner)view.findViewById(R.id.end_sigungu);

        layout_list = (ListView) view.findViewById(R.id.search_list);
        layout_map = (FrameLayout) view.findViewById(R.id.search_map);

        list_btn = (Button) view.findViewById(R.id.search_list_btn);
        map_btn = (Button) view.findViewById(R.id.search_map_btn);
        switch_btn = (Button) view.findViewById(R.id.switch_btn);

        SharedPreferences user_token = activity.getSharedPreferences("usertoken", MODE_PRIVATE);
        token = user_token.getString("token", null);

//        driverListData = new ArrayList<DriverSearchListData>();
//        mAdapter = new Driver_RecyclerAdapter(driverListData, clickEvent);
//        recyclerView.setAdapter(mAdapter);
//
//        mLayoutManager = new LinearLayoutManager(listContext);
//        Log.d("taehyungCC", "onViewCreated: mLayoutManager :" + mLayoutManager);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(mLayoutManager);

        adspin1 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_do, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);
        adspin3 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_do, android.R.layout.simple_spinner_dropdown_item);
        adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(adspin3);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adspin1.getItem(position).equals("시/도")){
                    start_choice_do = "시/군/구";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_sigungu,android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }else if(adspin1.getItem(position).equals("강원")) {
                    start_choice_do = "강원";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gangwon, android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (adspin1.getItem(position).equals("경기")) {
                    start_choice_do = "경기";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("경남")) {
                    start_choice_do = "경남";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("경북")) {
                    start_choice_do = "경북";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("광주")) {
                    start_choice_do = "광주";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gwangju, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("대구")) {
                    start_choice_do = "대구";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_daegu, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("대전")) {
                    start_choice_do = "대전";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_daejeon, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("부산")) {
                    start_choice_do = "부산";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_busan, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("서울")) {
                    start_choice_do = "서울";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_seoul, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("울산")) {
                    start_choice_do = "울산";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_ulsan, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("인천")) {
                    start_choice_do = "인천";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_incheon, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("전남")) {
                    start_choice_do = "전남";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("전북")) {
                    start_choice_do = "전북";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("제주")) {
                    start_choice_do = "제주";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeju, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("충남")) {
                    start_choice_do = "충남";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_chungnam, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin1.getItem(position).equals("충북")) {
                    start_choice_do = "충북";
                    adspin2 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            start_choice_se = adspin2.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adspin3.getItem(position).equals("시/도")) {
                    end_choice_do = "시/군/구";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_sigungu, android.R.layout.simple_spinner_dropdown_item);

                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin2);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("강원")) {
                    end_choice_do = "강원";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gangwon, android.R.layout.simple_spinner_dropdown_item);

                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (adspin3.getItem(position).equals("경기")) {
                    end_choice_do = "경기";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("경남")) {
                    end_choice_do = "경남";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("경북")) {
                    end_choice_do = "경북";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("광주")) {
                    end_choice_do = "광주";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_gwangju, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("대구")) {
                    end_choice_do = "대구";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_daegu, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("대전")) {
                    end_choice_do = "대전";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_daejeon, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("부산")) {
                    end_choice_do = "부산";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_busan, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("서울")) {
                    end_choice_do = "서울";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_seoul, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("울산")) {
                    end_choice_do = "울산";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_ulsan, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("인천")) {
                    end_choice_do = "인천";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_incheon, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("전남")) {
                    end_choice_do = "전남";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("전북")) {
                    end_choice_do = "전북";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("제주")) {
                    end_choice_do = "제주";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_jeju, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("충남")) {
                    end_choice_do = "충남";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_chungnam, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (adspin3.getItem(position).equals("충북")) {
                    end_choice_do = "충북";
                    adspin4 = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            end_choice_se = adspin4.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchBtn = (RelativeLayout) view.findViewById(R.id.searchbtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DriverInfo driverInfo = new DriverInfo();
                driverInfo.sloc = start_choice_se;
                driverInfo.eloc = end_choice_se;
                driverInfo.token = token;
                Log.d("taehyungCC", "onClick: sloc / eloc :" + driverInfo.sloc + " / " + driverInfo.eloc);
                Log.d("taehyungCC", "onClick: user_token: " + driverInfo.token);

                networkService = ApplicationController.getInstance().getNetworkService();
                Log.d("teahyungCC", "onClick: network: " + networkService);

                SharedPreferences user_sloc = activity.getSharedPreferences("userSloc", MODE_PRIVATE);
                SharedPreferences.Editor editor = user_sloc.edit();
                editor.putString("sloc", driverInfo.sloc);
                editor.commit();
                SharedPreferences user_eloc = activity.getSharedPreferences("userEloc", MODE_PRIVATE);
                SharedPreferences.Editor editor_e = user_eloc.edit();
                editor.putString("sloc", driverInfo.sloc);
                editor_e.commit();

//                DriverCurrentFragment listFragment= new DriverCurrentFragment(activity, mContext);
//                listFragment.search(driverInfo.sloc, driverInfo.eloc);

                Call<OwnerLocationListVO> requestDriverRegister = networkService.getDriverResult(driverInfo.sloc, driverInfo.eloc, driverInfo.token);
                requestDriverRegister.enqueue(new Callback<OwnerLocationListVO>() {
                    @Override
                    public void onResponse(Call<OwnerLocationListVO> call, Response<OwnerLocationListVO> response) {
                        Log.d("taehyungCC", "click searchBtn response.isSuccessful(): " + response.isSuccessful());
                        if (response.isSuccessful()) {
                            try{
                                Toast.makeText(mContext, "searchBtn response true", Toast.LENGTH_SHORT).show();

                                driverListData = response.body().result;
                                Log.d("tahyungCC", "driverListData: " + driverListData);

                                org.sopt.yata.yata.ui.driver.MatchingListAdapter adapter = new org.sopt.yata.yata.ui.driver.MatchingListAdapter(mContext, driverListData);
                                layout_list.setAdapter(adapter);
                                //연결 됐을 시 아래 List를 뿌려줘야함
                                layout_list.setVisibility(View.VISIBLE);


                            }catch(NullPointerException ne){
                                ne.printStackTrace();
                            }
                        }else {
                            Toast.makeText(mContext, "response.inSuccessful() 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<OwnerLocationListVO> call, Throwable t) {
                        Toast.makeText(mContext, "Fail !", Toast.LENGTH_SHORT).show();
                        Log.i("err", "" + t.getMessage());
                    }
                });

            }
        });

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.sopt.yata.yata.ui.driver.MatchingListAdapter adapter = new org.sopt.yata.yata.ui.driver.MatchingListAdapter(mContext, driverListData);   //(context, layout resource, listObject)
                layout_list.setAdapter(adapter);
                //연결 됐을 시 아래 List를 뿌려줘야함
                layout_list.setVisibility(View.VISIBLE);
            }
        });

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        switch_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int spin1Index = spin1.getSelectedItemPosition();
                final int spin2Index = spin2.getSelectedItemPosition();
                int spin3Index = spin3.getSelectedItemPosition();
                final int spin4Index = spin4.getSelectedItemPosition();

                spin1.setSelection(spin3Index);
                spin3.setSelection(spin1Index);

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        spin2.setSelection(spin4Index);
                        spin4.setSelection(spin2Index);
                    }
                }, 100);
            }
        });
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String tempSloc = driverListData.get(itemPosition).matching_sloc;

            final DialogMiniDetail miniDetail = new DialogMiniDetail(mContext);
            miniDetail.show();

        }
    };

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
}

