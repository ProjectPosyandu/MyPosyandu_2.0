package com.example.myposyandu.fragment_ibu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;
import com.example.myposyandu.activity.DetailBayiActivity;
import com.example.myposyandu.helper.ApiService;
import com.example.myposyandu.helper.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartFragment extends Fragment {
    Context mContext;
    ApiService mApiService;
    SharedPrefManager sharedPrefManager;

    String idBayi;
    private static final String TAG = "TAG";
    List<Number> beratBayi = new ArrayList<>();
    List<String> usiaList = new ArrayList<>();
    List<Number> kurangList = new ArrayList<>();
    List<Number> idealBawahList = new ArrayList<>();
    List<Number> idealList = new ArrayList<>();
    List<Number> idealAtasList = new ArrayList<>();
    List<Number> lebihList = new ArrayList<>();

    AnyChartView anyChartView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        anyChartView = root.findViewById(R.id.chart_bayi);
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getActivity());
        idBayi = sharedPrefManager.getSpIdBayi();
        String id_bayi = sharedPrefManager.getSpIdBayi();
        String jk = sharedPrefManager.getSpJk();
        beratBayi.clear();
        usiaList.clear();
        kurangList.clear();
        idealBawahList.clear();
        idealList.clear();
        idealAtasList.clear();
        lebihList.clear();
        if (jk.equals("Perempuan")){
            getChartDataGirls(id_bayi);
        }else {
            getChartDataBoys(id_bayi);
        }
        return root;
    }

    private void getChartDataBoys(String id_bayi){
        mApiService.getDataChartBoys(id_bayi).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonRESULTS.getJSONArray("data");
//                        JSONArray dataArray2 = jsonRESULTS.getJSONArray("data2");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            usiaList.add(dataobj.getString("usia"));
                            kurangList.add(dataobj.getDouble("kurang"));
                            idealBawahList.add(dataobj.getDouble("ideal_bawah"));
                            idealList.add(dataobj.getDouble("ideal"));
                            idealAtasList.add(dataobj.getDouble("ideal_atas"));
                            lebihList.add(dataobj.getDouble("lebih"));
                            beratBayi.add(dataobj.getDouble("bb"));
                        }

//                        for (int i = 0; i < dataArray2.length(); i++) {
//                            JSONObject dataobj = dataArray2.getJSONObject(i);
//                            beratBayi.add(dataobj.getDouble("bb"));
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    entryData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
 }

    private void getChartDataGirls(String id_bayi){
        mApiService.getDataChartGirls(id_bayi).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonRESULTS.getJSONArray("data");
//                        JSONArray dataArray2 = jsonRESULTS.getJSONArray("data2");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            usiaList.add(dataobj.getString("usia"));
                            kurangList.add(dataobj.getDouble("kurang"));
                            idealBawahList.add(dataobj.getDouble("ideal_bawah"));
                            idealList.add(dataobj.getDouble("ideal"));
                            idealAtasList.add(dataobj.getDouble("ideal_atas"));
                            lebihList.add(dataobj.getDouble("lebih"));
                            beratBayi.add(dataobj.getDouble("bb"));
                        }

//                        for (int i = 0; i < dataArray2.length(); i++) {
//                            JSONObject dataobj = dataArray2.getJSONObject(i);
//                            beratBayi.add(dataobj.getDouble("bb"));
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    entryData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }

 private void entryData() {
    Cartesian cartesian = AnyChart.line();
    cartesian.animation(true);
    cartesian.padding(10d, 20d, 5d, 20d);
    cartesian.crosshair().enabled(true);
    cartesian.crosshair()
          .yLabel(true)
          // TODO ystroke
          .yStroke((Stroke) null, null, null, (String) null, (String) null);
    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
    cartesian.title("GRAFIK PERTUMBUHAN BAYI");
    cartesian.yAxis(0).title("Berat Badan (Kg)");
    cartesian.yAxis(0).labels().padding(5d, 5d, 5d, 5d);
    cartesian.xAxis(0).title("Usia (bulan)");
    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

    List<DataEntry> seriesData = new ArrayList<>();
    int i = 0;
    while (i<usiaList.size()){
        seriesData.add(new ChartFragment.CustomDataEntry(usiaList.get(i),beratBayi.get(i),kurangList.get(i),idealBawahList.get(i),
            idealList.get(i),idealAtasList.get(i),lebihList.get(i)));
        i++;
    }
    Set set = Set.instantiate();
    set.data(seriesData);
    Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
    Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
    Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
    Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
    Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
    Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");

     Line series1 = cartesian.line(series1Mapping);
     series1.name("Berat Bayi");
     series1.hovered().markers().enabled(true);
     series1.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series1.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     Line series2 = cartesian.line(series2Mapping);
     series2.name("Kurang");
     series2.hovered().markers().enabled(true);
     series2.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series2.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     Line series3 = cartesian.line(series3Mapping);
     series3.name("Ideal Bawah");
     series3.hovered().markers().enabled(true);
     series3.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series3.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     Line series4 = cartesian.line(series4Mapping);
     series4.name("Ideal");
     series4.hovered().markers().enabled(true);
     series4.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series4.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     Line series5 = cartesian.line(series5Mapping);
     series5.name("Ideal Atas");
     series5.hovered().markers().enabled(true);
     series5.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series5.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     Line series6 = cartesian.line(series6Mapping);
     series6.name("Lebih");
     series6.hovered().markers().enabled(true);
     series6.hovered().markers().type(MarkerType.CIRCLE).size(4d);
     series6.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

     cartesian.legend().enabled(true);
     cartesian.legend().fontSize(13d);
     cartesian.legend().padding(0d, 0d, 10d, 0d);
     anyChartView.setChart(cartesian);
 }

 public class CustomDataEntry extends ValueDataEntry {
      CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4, Number value5, Number value6) {
       super(x, value);
       setValue("value2", value2);
       setValue("value3", value3);
       setValue("value4", value4);
       setValue("value5", value5);
       setValue("value6", value6);
  }
 }

    @Override
    public void onResume(){
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent intent = new Intent(getActivity(), DetailBayiActivity.class);
                    intent.putExtra("id_bayi", idBayi);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

}
