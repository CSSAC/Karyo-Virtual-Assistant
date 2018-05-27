package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;

import java.util.List;

public class GraphViewAnalytics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_analytics);

        Intent intent = getIntent();
        List<Integer> list = (List<Integer>) intent.getSerializableExtra("ANALYTICS_LIST");
        String title = (String) intent.getSerializableExtra("TITLE");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[list.size()];
        for (int i = 0;i<list.size();i++) {
            dataPoints[i] = new DataPoint(i, list.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.addSeries(series);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        graph.setBackgroundColor(Color.argb(0, 50, 0, 200));
        series.setDrawBackground(true);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Score");
        gridLabel.setHorizontalAxisTitle("game-plays");

        graph.setTitle(title);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(10.0);
        graph.getViewport().setYAxisBoundsManual(true);
    }
}
