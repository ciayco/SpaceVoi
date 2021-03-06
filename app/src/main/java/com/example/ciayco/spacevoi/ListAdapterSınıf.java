package com.example.ciayco.spacevoi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ListAdapterSınıf extends BaseExpandableListAdapter{
    private Context ctx;
    private HashMap<String, List<String>> Movies_Category;
    private List<String> Movies_List;

    public ListAdapterSınıf(Context ctx, HashMap<String, List<String>> Movies_Category, List<String> Movies_List)
    {
        this.ctx = ctx;
        this.Movies_Category = Movies_Category;
        this.Movies_List = Movies_List;
    }

    @Override
    public int getGroupCount() {
        return Movies_List.size();
    }

    @Override
    public int getChildrenCount(int arg0) {
        return Movies_Category.get(Movies_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        return Movies_List.get(arg0);
    }

    @Override
    public Object getChild(int parent, int child) {
        return Movies_Category.get(Movies_List.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentview) {
        String group_title = (String) getGroup(parent);

        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout, parentview,false);
        }

        TextView parent_textview= (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);

        return convertView;
    }


    KaydetCalSınıf ka = new KaydetCalSınıf();
    UpDownSınıf us = new UpDownSınıf();


    @Override
    public View getChildView(final int parent, int child, boolean lastChild, View convertView, ViewGroup parentview) {
        final String child_title = (String) getChild(parent, child);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout, parentview,false);
        }
    TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
    child_textview.setText(child_title);
    child_textview.setTextColor(Color.parseColor("#bdbdbd"));



        //region "Cal Buton
        ImageButton calbut =
                (ImageButton)convertView.findViewById( R.id.play_button);
        calbut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (ka.playerkontrol()) {
                            String group_title = (String) getGroup(parent);
                            String grup;
                            if (group_title=="Pool Kayıtları")
                                grup = "pool";
                                else
                                grup = "profil";

                            ka.startPlaying(child_title,ctx,grup);
                        } else
                            ka.stopPlaying();
                    }
                });
        //endregion


        //region "Like Buton

        ImageButton likebut =
                (ImageButton)convertView.findViewById( R.id.like_button);
        likebut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        String durum = "begeni";
                        us.Begen(child_title,durum);
                    }
                });

        //endregion

        return convertView;
    }

    //child verisi çekmek için
    public String getChildData(int parent, int child) {
        String child_title = (String) getChild(parent, child);

        return child_title;
    }

    @Override

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
