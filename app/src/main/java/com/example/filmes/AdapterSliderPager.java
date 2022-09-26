package com.example.filmes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSliderPager extends PagerAdapter {

    private Context mContext;
    private List<Slider> mlista;


    public AdapterSliderPager(Context mContext, List<Slider> mlista) {
        this.mContext = mContext;
        this.mlista = mlista;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = layoutInflater.inflate(R.layout.slider_item, null);

        ImageView slideImg = slideLayout.findViewById(R.id.slider_img);
        TextView slideTitle = slideLayout.findViewById(R.id.slider_title);
        TextView slideVote = slideLayout.findViewById(R.id.slider_vote);

        Slider slider = mlista.get(position);

        slideTitle.setText(slider.getTitle());
        slideVote.setText(slider.getVote().toString());
        Picasso.get()
                .load(slider.getImage())
                .into(slideImg);

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mlista.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
