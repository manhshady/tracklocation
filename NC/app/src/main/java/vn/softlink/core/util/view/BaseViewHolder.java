package vn.softlink.core.util.view;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.ButterKnife;

/**
 * --------------------------------------------------------------------------------
 *
 * @Project: pimp
 * @Created: Huy QV 2018/09/24
 * @Description: ...
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBind(M model, int position);

    public void animate(@AnimRes int anim) {
        Animation a = AnimationUtils.loadAnimation(itemView.getContext(), anim);
        a.setDuration(400);
        a.setFillAfter(true);
        itemView.startAnimation(a);
    }

    public Context getContext() {
        return itemView.getContext();
    }
}
