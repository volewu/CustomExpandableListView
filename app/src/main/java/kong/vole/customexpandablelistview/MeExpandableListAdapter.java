package kong.vole.customexpandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 编写者： Wu
 * Time： 16-12-11.16:27
 * 内容：
 */
public class MeExpandableListAdapter extends BaseExpandableListAdapter {
    //分组视图（group）的标题
    private String[] group = {"group1", "group2"};
    //分组视图（group）的图标
    private int[] groupicon = {R.mipmap.group_icon_1, R.mipmap.group_icon_2};
    //子选项视图(child) 的标题
    private String[][] gridViewChild = {{"child11", "child12"}, {"child21", "child22"}};
    //子选项视图(child) 的图标
    private int[][] gridImgChild = new int[][]{
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher}
    };
    private String[][] child = {{""}, {""}};

    private LayoutInflater mInflater;
    private Context context;

    public MeExpandableListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //expandablelistview的分组数
    @Override
    public int getGroupCount() {
        return group.length;
    }

    //取得指定分组的子元素数
    @Override
    public int getChildrenCount(int groupPosition) {
        return child[groupPosition].length;
    }

    //取得与给定分组关联的数据
    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    //取得与给定子分组关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child[groupPosition][childPosition];
    }

    //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //重写GroupView的布局
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewChild = new ViewChild();
            convertView = mInflater.inflate(R.layout.expandablelistview_group, parent, false);
            mViewChild.textView = (TextView) convertView
                    .findViewById(R.id.group_name);
            mViewChild.imageView = (ImageView) convertView
                    .findViewById(R.id.group_indicator);
            mViewChild.imageHead = (ImageView) convertView
                    .findViewById(R.id.group_icon);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (ViewChild) convertView.getTag();
        }
        if (isExpanded)
            mViewChild.imageView.setImageResource(R.mipmap.group_indicator_2);
        else
            mViewChild.imageView.setImageResource(R.mipmap.group_indicator_1);

        mViewChild.textView.setText(getGroup(groupPosition).toString());
        mViewChild.imageHead.setImageResource(groupicon[groupPosition]);
        return convertView;
    }

    //重写ChildView的布局
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewChild = new ViewChild();
            convertView = mInflater.inflate(
                    R.layout.expandablelistview_chilld, parent, false);
            mViewChild.gridView = (GridView) convertView
                    .findViewById(R.id.child_gridView);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (ViewChild) convertView.getTag();
        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(context,
                setGridViewData(gridViewChild[groupPosition],
                        gridImgChild[groupPosition]),
                R.layout.chilld_gridview_item, new String[]{
                "child_gridview_item", "child_gridview_img1"},
                new int[]{R.id.child_gridview_item,
                        R.id.child_gridview_icon});
        mViewChild.gridView.setAdapter(mSimpleAdapter);
        setGridViewListener(mViewChild.gridView, groupPosition);
        return convertView;
    }

    //设置GridView点击事件监听
    private void setGridViewListener(final GridView gridView, final int groupPosition) {
        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RelativeLayout ff = (RelativeLayout) view;
                TextView gg = (TextView) ff
                        .findViewById(R.id.child_gridview_item);
                Toast.makeText(context, "你点击了" + gg.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //设置GridView数据
    private ArrayList<HashMap<String, Object>> setGridViewData(String[] data,
                                                               int[] img) {
        ArrayList<HashMap<String, Object>> gridItem = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("child_gridview_item", data[i]);
            hashMap.put("child_gridview_img1", img[i]);
            gridItem.add(hashMap);
        }
        return gridItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    ViewChild mViewChild;

    static class ViewChild {
        ImageView imageHead;
        ImageView imageView;
        TextView textView;
        GridView gridView;
    }
}
