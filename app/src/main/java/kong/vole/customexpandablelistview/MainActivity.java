package kong.vole.customexpandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        //自定义适配器
        MeExpandableListAdapter mExpandableListAdapter = new MeExpandableListAdapter(MainActivity.this) ;
        expandableListView.setAdapter(mExpandableListAdapter);
        //默认展开的是那个group
        expandableListView.expandGroup(0);
    }
}
