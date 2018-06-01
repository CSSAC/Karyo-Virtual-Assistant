package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class GraphViewAnalytics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_analytics);

        Intent intent = getIntent();
        List<Integer> list = (List<Integer>) intent.getSerializableExtra("SCORE_LIST");
        List<Integer> accuracy_list = (List<Integer>) intent.getSerializableExtra("ACCURACY_LIST");
        String title = (String) intent.getSerializableExtra("TITLE");

        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(title.toUpperCase());

        GraphView graph = (GraphView) findViewById(R.id.graph);
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);

        DataPoint[] dataPoints = new DataPoint[list.size()];
        DataPoint[] dataPoints2 = new DataPoint[accuracy_list.size()];
        for (int i = 0;i<list.size();i++) {
            dataPoints[i] = new DataPoint(i, list.get(i));
            dataPoints2[i] = new DataPoint(i, accuracy_list.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dataPoints2);
        graph.addSeries(series);
        graph2.addSeries(series2);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        graph.setBackgroundColor(Color.argb(0, 50, 0, 200));
        graph2.setBackgroundColor(Color.argb(0, 200, 0, 50));
        series.setDrawBackground(true);
        series2.setDrawBackground(true);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Score");

        GridLabelRenderer gridLabel2 = graph2.getGridLabelRenderer();
        gridLabel2.setVerticalAxisTitle("Accuracy");

        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(10.0);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(list.size()+1);
        graph.getViewport().setXAxisBoundsManual(true);

        graph2.getViewport().setMinY(0.0);
        graph2.getViewport().setMaxY(100.0);
        graph2.getViewport().setYAxisBoundsManual(true);

        graph2.getViewport().setMinX(0.0);
        graph2.getViewport().setMaxX(accuracy_list.size()+1);
        graph2.getViewport().setXAxisBoundsManual(true);
    }
}
