package com.syd.arknights;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;

public class EmployAdapter extends ArrayAdapter<employee> {

    private int resourseID;

    public EmployAdapter(Context context, int textViewResourceID, List<employee> objects){
        super(context,textViewResourceID,objects);
        resourseID=textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        employee ep=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseID,parent,false);
        TextView name=(TextView)view.findViewById(R.id.item1);
        TextView rarity=(TextView)view.findViewById(R.id.item2);
        ImageView location = (ImageView)view.findViewById(R.id.imageView3);
        ImageView touxiang = (ImageView)view.findViewById(R.id.touxiang);
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

        // UPPERCASE：大写  (ZHONG)
        // LOWERCASE：小写  (zhong)
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//输出小写

        // WITHOUT_TONE：无音标  (zhong)
        // WITH_TONE_NUMBER：1-4数字表示音标  (zhong4)
        // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        // WITH_V：用v表示ü  (nv)
        // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
        // WITH_U_UNICODE：直接用ü (nü)
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String n="";
        int i=0;
        while(i<ep.Name.length()){
            try{
                String[] a = PinyinHelper.toHanyuPinyinStringArray(ep.Name.charAt(i++),format);
                n+=a[0];
            }catch (BadHanyuPinyinOutputFormatCombination e){

            }
        }
        name.setText(ep.Name);
        String lo = ep.getLocation();
        Uri uri1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.arknights/drawable/" + lo);
        location.setImageURI(uri1);
        Uri uri2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.arknights/drawable/" + n);
        touxiang.setImageURI(uri2);
        switch (ep.rarity)
        {
            case 6:
                rarity.setText("★★★★★★");
                view.setBackgroundColor(Color.rgb(255,153 ,18));
                break;
            case 5:
                rarity.setText("★★★★★");
                view.setBackgroundColor(Color.rgb(255,248 ,163));
                rarity.setTextColor(Color.BLACK);
                break;
            case 4:
                rarity.setText("★★★★");
                view.setBackgroundColor(Color.rgb(106,90 ,205));
                break;
            case 3:
                rarity.setText("★★★");
                view.setBackgroundColor(Color.rgb(220,220 ,220));
                break;
            default:
                break;
        }
        return view;
    }
}
