package vn.softlink.core.util.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public abstract class BaseRecyclerAdapter<M, H extends BaseViewHolder<M>> extends RecyclerView.Adapter<H> {


    public interface ItemClickListener<M> {
        void onItemClick(M model, int position);
    }

    public interface ItemLongClickListener<M> {
        void onItemLongClick(M model, int position);
    }

    /**
     * [BaseRecyclerAdapter] abstract initialize
     */
    @LayoutRes
    protected abstract int getItemLayout(M model);

    protected abstract H getViewHolder(@NonNull View view, int layout);

    protected void onBindView(@NonNull BaseViewHolder holder, @NonNull M model, int position) {
        holder.onBind(model, position);
    }


    /**
     * [RecyclerView.Adapter] overriding
     */
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return getViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        M model = getModel(position);
        if (null != model) {
            onBindView(holder, model, position);
            if (null != mClickListener) {
                holder.itemView.setOnClickListener(v ->
                        mClickListener.onItemClick(model, position)
                );
            }
            if (null != mLongClickListener) {
                holder.itemView.setOnLongClickListener(v -> {
                    mLongClickListener.onItemLongClick(model, position);
                    return true;
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(getModel(position));
    }


    /**
     * Overridable data list handle
     */
    private List<M> mList;

    public ItemClickListener<M> mClickListener;

    public ItemLongClickListener<M> mLongClickListener;

    @Nullable
    public M getModel(int position) {
        return indexInBound(position) ? mList.get(position) : null;
    }

    @Nullable
    public List<M> getData() {
        return mList;
    }

    public void setData(@Nullable Collection<M> collection) {
        if (null == collection || collection.isEmpty()) {
            mList = null;
        } else {
            checkData();
            mList.addAll(collection);
            notifyDataSetChanged();
        }

    }

    public void setData(@Nullable M... models) {
        if (null == models || models.length == 0) {
            mList = null;
        } else {
            checkData();
            mList.addAll(Arrays.asList(models));
            notifyDataSetChanged();
        }

    }

    public void add(@Nullable M model) {
        if (null != model) {
            checkData();
            mList.add(model);
            notifyDataSetChanged();
        }
    }

    public void add(@Nullable Collection<M> models) {
        if (isNotEmpty(models)) {
            checkData();
            mList.addAll(models);
            notifyDataSetChanged();
        }
    }

    public void add(@Nullable M... models) {
        if (isNotEmpty(models)) {
            checkData();
            mList.addAll(Arrays.asList(models));
            notifyDataSetChanged();
        }
    }

    public void remove(@Nullable M model) {
        if (null != mList && !mList.isEmpty()) {
            mList.remove(model);
            notifyDataSetChanged();
        }
    }

    public void remove(@Nullable M... models) {
        if (null != models && models.length != 0) {
            return;
        }
        for (M model : models) {
            mList.remove(model);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (indexInBound(position)) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void remove(int... positionArray) {
        if (null != positionArray && positionArray.length != 0) {
            return;
        }
        for (int position : positionArray) {
            mList.remove(position);
        }
        notifyDataSetChanged();
    }

    public void edit(int position, @Nullable M model) {
        if (null != model && indexInBound(position)) {
            mList.set(position, model);
        }
    }

    public void editByHash(@Nullable M model) {
        if (null == model || null == mList)
            return;
        for (int i = 0; i < mList.size(); i++) {
            if (-1 != model.hashCode() && model.hashCode() == getModel(i).hashCode()) {
                mList.set(i, model);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void editByString(@Nullable M model) {
        if (null == model || null == mList)
            return;
        for (int i = 0; i < mList.size(); i++) {
            if (!TextUtils.isEmpty(model.toString()) && model.toString().equals(getModel(i).toString())) {
                mList.set(i, model);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public boolean isEmpty() {
        if (null == mList) {
            return true;
        }
        if (mList.size() == 0) {
            return true;
        }
        return false;

    }

    public int size() {
        return null == mList ? 0 : mList.size();
    }

    public void clear() {
        mList = null;
        notifyDataSetChanged();
    }

    public int lastPosition() {
        return size() - 1;
    }

    public void setItemClickListener(@Nullable ItemClickListener<M> listener) {
        mClickListener = listener;
    }

    public void setItemLongClickListener(@Nullable ItemLongClickListener<M> listener) {
        mLongClickListener = listener;
    }


    /**
     * Private utility methods
     */
    private void checkData() {
        if (null == mList) {
            mList = new ArrayList<>();
        }
    }

    private boolean indexInBound(int position) {
        return null != mList && position > -1 && position < mList.size();
    }

    private boolean isNotEmpty(@Nullable Collection<M> collection) {
        return null != collection && !collection.isEmpty();
    }

    private boolean isNotEmpty(@Nullable M... array) {
        return null != array && array.length != 0;
    }
}
