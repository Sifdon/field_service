package com.agit.bp.mechanicbp.adapters;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agit.bp.mechanicbp.R;


/**
 * Created by NiyatiR on 10/13/2016.
 */
public class SlidingImageAdapter extends PagerAdapter {

    private String[] images;
    private LayoutInflater inflater;
    private Activity activity;

    public SlidingImageAdapter(Activity activity, String[] images) {
        this.activity = activity;
        this.images = images;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.swipe_fragment, view, false);

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imageView);
        int imgResId = activity.getResources().getIdentifier(images[position], "drawable", "com.agit.bp.mechanicbp");
        imageView.setImageResource(imgResId);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}