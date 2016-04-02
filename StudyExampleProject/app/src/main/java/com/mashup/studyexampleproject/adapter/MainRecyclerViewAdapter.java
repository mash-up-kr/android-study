package com.mashup.studyexampleproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mashup.studyexampleproject.R;
import com.mashup.studyexampleproject.model.ContactItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Omjoon on 16. 3. 24..
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ContactItem> list;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;
    public interface onEventListener{
        void onAdd(String name,String callNumber);
        void onClick(ContactItem data);
    }
    private onEventListener listener;
    public MainRecyclerViewAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
        setData();
    }

    public void setListener(onEventListener listener){
        this.listener = listener;
    }

    public void setData(){
        list.clear();
        list.add(new ContactItem("01026132058","고민규", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","김동휘", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","김범준", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","김태경", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","김윤영", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","김정민", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","유슬기", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","조은선", R.mipmap.ic_launcher));
        list.add(new ContactItem("01026132058","양소현", R.mipmap.ic_launcher));
    }

    public void addItem(ContactItem item){
        list.add(0,item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case TYPE_HEADER :
                view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_main_header,parent,false);
                return new HeaderViewHolder(view);
            case TYPE_CONTENT :
                view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_main_item,parent,false);
                return new ItemViewHolder(view);
            case TYPE_FOOTER :
                view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_main_footer,parent,false);
                return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
                ((ItemViewHolder) holder).update(list.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : position == list.size() ? TYPE_FOOTER : TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return list.size()+2;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        EditText name;

        @Bind(R.id.callNumber)
        EditText callNumber;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.add)
        public void onAdd(){
            String str_name = name.getText().toString().trim();
            if(str_name.length() == 0){
                Toast.makeText(mContext,"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                return;
            }
            listener.onAdd(str_name,callNumber.getText().toString());
            name.setText("");
            callNumber.setText("");
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(callNumber.getWindowToken(), 0);
        }


    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.callNumber)
        TextView callNumber;

        @Bind(R.id.image)
        ImageView image;

        View view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.view = itemView;
        }

        public void update(final ContactItem data){
            name.setText(data.getName());
            callNumber.setText(data.getCallNumber());
            image.setImageResource(data.getImageId());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(data);
                }
            });
        }
    }


    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


}
