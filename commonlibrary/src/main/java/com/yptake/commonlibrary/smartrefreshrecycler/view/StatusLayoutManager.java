package com.yptake.commonlibrary.smartrefreshrecycler.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.yptake.commonlibrary.R;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnStatusLayoutClickListener;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnStatusLayoutClickChildListener;

/**
 * 状态布局包括了 基础的 加载中 空布局 错误布局 无网络布局等4种  也可以自定义布局~
 */
public class StatusLayoutManager {

    /**
     * 加载中布局
     */
    private static final int DEFAULT_LOADING_LAYOUT_ID = R.layout.layout_status_layout_manager_loading;
    /**
     * 空布局
     */
    private static final int DEFAULT_EMPTY_LAYOUT_ID = R.layout.layout_status_layout_manager_empty;
    /**
     * 错误布局
     */
    private static final int DEFAULT_ERROR_LAYOUT_ID = R.layout.layout_status_layout_manager_error;
    /**
     * 没有网络的时候
     */
    private static final int DEFAULT_NO_NETWORK_LAYOUT_ID = R.layout.layout_status_layout_manager_no_network;


    /**
     * 默认背景颜色
     */
    private static final int DEFAULT_BACKGROUND_COLOR = R.color.white100;

    private View contentLayout;
    /**
     * 加载中
     */
    @LayoutRes
    private int loadingLayoutID;
    private View loadingLayout;

    /**
     * 空白
     */
    @IdRes
    private int[] emptyClickViewIds;
    @LayoutRes
    private int emptyLayoutID;
    private View emptyLayout;

    /**
     * 错误
     */
    @IdRes
    private int[] errorClickViewIds;
    @LayoutRes
    private int errorLayoutID;
    private View errorLayout;

    /**
     * 无网络
     */
    @IdRes
    private int[] noNetworkClickViewIds;
    @LayoutRes
    private int noNetWorkLayoutID;
    private View noNetWorkLayout;

    private int defaultBackgroundColor;
    /**
     * 布局点击事件
     */
    private OnStatusLayoutClickListener onStatusLayoutClickListener;
    /**
     * 子view的点击事件
     */
    private OnStatusLayoutClickChildListener onStatusLayoutClickChildListener;

    private ReplaceLayoutHelper replaceLayoutHelper;

    private LayoutInflater inflater;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;

        this.emptyClickViewIds = builder.emptyClickViewIds;
        this.emptyLayoutID = builder.emptyLayoutID;
        this.emptyLayout = builder.emptyLayout;

        this.errorClickViewIds = builder.errorClickViewIds;
        this.errorLayoutID = builder.errorLayoutID;
        this.errorLayout = builder.errorLayout;

        this.noNetworkClickViewIds = builder.noNetworkClickViewIds;
        this.noNetWorkLayoutID = builder.noNetWorkLayoutID;
        this.noNetWorkLayout = builder.noNetWorkLayout;

        this.defaultBackgroundColor = builder.defaultBackgroundColor;

        this.onStatusLayoutClickListener = builder.onStatusLayoutClickListener;
        this.onStatusLayoutClickChildListener = builder.onStatusLayoutClickChildListener;

        this.replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource, null);
    }

    ///////////////////////////////////////////
    /////////////////原有布局////////////////////
    ///////////////////////////////////////////

    /**
     * 显示原有布局
     */
    public void showSuccessLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    ///////////////////////////////////////////
    ////////////////加载中布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建加载中布局
     */
    private void createLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
        }
        if (loadingLayoutID == DEFAULT_LOADING_LAYOUT_ID) {
            loadingLayout.setBackgroundColor(defaultBackgroundColor);
        }
    }

    /**
     * 获取加载中布局
     *
     * @return 加载中布局
     */
    public View getLoadingLayout() {
        createLoadingLayout();
        return loadingLayout;
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        createLoadingLayout();
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    ///////////////////////////////////////////
    ////////////////空数据布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建空数据布局
     */
    @SuppressLint("ResourceType")
    private void createEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID);
        }
        if (emptyLayoutID == DEFAULT_EMPTY_LAYOUT_ID) {
            emptyLayout.setBackgroundColor(defaultBackgroundColor);
        }

        // 整个布局的点击事件
        if (onStatusLayoutClickListener != null && emptyLayout != null) {
            View clickView = emptyLayout.findViewById(R.id.ll_empty_view);
            if (clickView == null) {
                emptyLayout.setOnClickListener(v -> onStatusLayoutClickListener.onEmptyClick(emptyLayout));
            } else {
                clickView.setOnClickListener(v -> onStatusLayoutClickListener.onEmptyClick(clickView));
            }
        }

        if (onStatusLayoutClickChildListener == null || emptyLayout == null) {
            return;
        }
        if (emptyClickViewIds == null || emptyClickViewIds.length == 0) {
            return;
        }
        // 点击事件回调
        for (int aClickViewID : emptyClickViewIds) {
            View clickView = emptyLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }
            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(view1 -> onStatusLayoutClickChildListener.onEmptyChildClick(view1));
        }

    }

    /**
     * 获取空数据布局
     *
     * @return 空数据布局
     */
    public View getEmptyLayout() {
        createEmptyLayout();
        return emptyLayout;
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        createEmptyLayout();
        replaceLayoutHelper.showStatusLayout(emptyLayout);
    }

    ///////////////////////////////////////////
    /////////////////出错布局////////////////////
    ///////////////////////////////////////////

    /**
     * 创建出错布局
     */
    @SuppressLint("ResourceType")
    private void createErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID);
        }
        if (errorLayoutID == DEFAULT_ERROR_LAYOUT_ID) {
            errorLayout.setBackgroundColor(defaultBackgroundColor);
        }

        // 整个布局的点击事件
        if (onStatusLayoutClickListener != null && errorLayout != null) {
            View clickView = errorLayout.findViewById(R.id.ll_error_view);
            if (clickView == null) {
                errorLayout.setOnClickListener(v -> onStatusLayoutClickListener.onErrorClick(errorLayout));
            } else {
                clickView.setOnClickListener(v -> onStatusLayoutClickListener.onErrorClick(clickView));
            }
        }

        if (onStatusLayoutClickChildListener == null || errorLayout == null) {
            return;
        }
        if (errorClickViewIds == null || errorClickViewIds.length == 0) {
            return;
        }
        // 点击事件回调
        for (int aClickViewID : errorClickViewIds) {
            View clickView = errorLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }
            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(view1 -> onStatusLayoutClickChildListener.onErrorChildClick(view1));
        }

    }

    /**
     * 获取出错布局
     *
     * @return 出错布局
     */
    public View getErrorLayout() {
        createErrorLayout();
        return errorLayout;
    }

    /**
     * 显示出错布局
     */
    public void showErrorLayout() {
        createErrorLayout();
        replaceLayoutHelper.showStatusLayout(errorLayout);
    }

    ///////////////////////////////////////////
    /////////////////无网络布局////////////////////
    ///////////////////////////////////////////

    /**
     * 创建无网络布局
     */
    @SuppressLint("ResourceType")
    private void createNoNetworkLayout() {
        if (noNetWorkLayout == null) {
            noNetWorkLayout = inflate(noNetWorkLayoutID);
        }
        if (noNetWorkLayoutID == DEFAULT_NO_NETWORK_LAYOUT_ID) {
            noNetWorkLayout.setBackgroundColor(defaultBackgroundColor);
        }
        // 整个布局的点击事件
        if (onStatusLayoutClickListener != null && noNetWorkLayout != null) {
            View clickView = noNetWorkLayout.findViewById(R.id.ll_no_network_view);
            if (clickView == null) {
                noNetWorkLayout.setOnClickListener(v -> onStatusLayoutClickListener.onNoNetWorkClick(noNetWorkLayout));
            } else {
                clickView.setOnClickListener(v -> onStatusLayoutClickListener.onNoNetWorkClick(clickView));
            }
        }

        if (onStatusLayoutClickChildListener == null || noNetWorkLayout == null) {
            return;
        }
        if (noNetworkClickViewIds == null || noNetworkClickViewIds.length == 0) {
            return;
        }
        // 点击事件回调
        for (int aClickViewID : noNetworkClickViewIds) {
            View clickView = noNetWorkLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }
            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(view1 -> onStatusLayoutClickChildListener.onNoNetWorkChildClick(view1));

        }

    }

    /**
     * 获取无网络布局
     *
     * @return 无网络布局
     */
    public View getNoNetworkLayout() {
        createNoNetworkLayout();
        return noNetWorkLayout;
    }

    /**
     * 显示无网络布局
     */
    public void showNoNetwork() {
        createNoNetworkLayout();
        replaceLayoutHelper.showStatusLayout(noNetWorkLayout);
    }

    ///////////////////////////////////////////
    ////////////////自定义布局///////////////////
    ///////////////////////////////////////////

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     */
    public void showCustomLayout(@NonNull View customLayout) {
        replaceLayoutHelper.showStatusLayout(customLayout);
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义状态布局 ID
     * @return 通过 customLayoutID 生成的 View
     */
    public View showCustomLayout(@LayoutRes int customLayoutID) {
        View customerView = inflate(customLayoutID);
        showCustomLayout(customerView);
        return customerView;
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @param clickViewID  可点击 View ID
     */
    public void showCustomLayout(@NonNull View customLayout, @IdRes int... clickViewID) {
        replaceLayoutHelper.showStatusLayout(customLayout);

        // 整个布局的点击事件
        if (onStatusLayoutClickListener != null) {
            noNetWorkLayout.setOnClickListener(v -> onStatusLayoutClickListener.onCustomerClick(customLayout));
        }

        if (onStatusLayoutClickChildListener == null) {
            return;
        }

        for (int aClickViewID : clickViewID) {
            View clickView = customLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }

            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(view -> onStatusLayoutClickChildListener.onCustomerChildClick(view));
        }

    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param clickViewID    点击按钮 ID
     */
    public View showCustomLayout(@LayoutRes int customLayoutID, @IdRes int... clickViewID) {
        View customLayout = inflate(customLayoutID);
        showCustomLayout(customLayout, clickViewID);
        return customLayout;
    }

    public void setOnStatusLayoutClickListener(OnStatusLayoutClickListener onStatusLayoutClickListener) {
        this.onStatusLayoutClickListener = onStatusLayoutClickListener;
    }

    public void setOnStatusLayoutClickChildListener(OnStatusLayoutClickChildListener onStatusLayoutClickChildListener) {
        this.onStatusLayoutClickChildListener = onStatusLayoutClickChildListener;
    }

    public static final class Builder {

        private View contentLayout;

        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;

        @IdRes
        private int[] emptyClickViewIds;
        @LayoutRes
        private int emptyLayoutID;
        private View emptyLayout;

        @IdRes
        private int[] errorClickViewIds;
        @LayoutRes
        private int errorLayoutID;
        private View errorLayout;

        @IdRes
        private int[] noNetworkClickViewIds;
        @LayoutRes
        private int noNetWorkLayoutID;
        private View noNetWorkLayout;

        private int defaultBackgroundColor;

        /**
         * 布局点击事件
         */
        private OnStatusLayoutClickListener onStatusLayoutClickListener;
        /**
         * 子view的点击事件
         */
        private OnStatusLayoutClickChildListener onStatusLayoutClickChildListener;

        /**
         * 创建状态布局 Build 对象
         *
         * @param contentLayout 原有布局，内容布局
         */
        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            // 设置默认布局
            this.loadingLayoutID = DEFAULT_LOADING_LAYOUT_ID;
            this.emptyLayoutID = DEFAULT_EMPTY_LAYOUT_ID;
            this.errorLayoutID = DEFAULT_ERROR_LAYOUT_ID;
            this.noNetWorkLayoutID = DEFAULT_NO_NETWORK_LAYOUT_ID;
            // 设置默认背景色
            this.defaultBackgroundColor = contentLayout.getContext().getResources().getColor(DEFAULT_BACKGROUND_COLOR);
        }

        ///////////////////////////////////////////
        ////////////////加载中布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置加载中布局
         *
         * @param loadingLayoutID 加载中布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@LayoutRes int loadingLayoutID) {
            this.loadingLayoutID = loadingLayoutID;
            return this;
        }

        /**
         * 设置加载中布局
         *
         * @param loadingLayout 加载中布局
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@NonNull View loadingLayout) {
            this.loadingLayout = loadingLayout;
            return this;
        }


        ///////////////////////////////////////////
        ////////////////空数据布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置空数据布局
         *
         * @param emptyClickViewIds 空数据布局需要点击事件的 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayoutChildClickIds(@LayoutRes int... emptyClickViewIds) {
            this.emptyClickViewIds = emptyClickViewIds;
            return this;
        }

        /**
         * 设置空数据布局
         *
         * @param emptyLayoutResId 空数据布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@LayoutRes int emptyLayoutResId) {
            this.emptyLayoutID = emptyLayoutResId;
            return this;
        }

        /**
         * 设置空数据布局
         *
         * @param emptyLayout 空数据布局
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@NonNull View emptyLayout) {
            this.emptyLayout = emptyLayout;
            return this;
        }


        ///////////////////////////////////////////
        /////////////////出错布局////////////////////
        ///////////////////////////////////////////

        /**
         * 设置出错布局
         *
         * @param errorClickViewIds 出错布局需要点击事件的 ID
         * @return 状态布局 Build 对象
         */
        public Builder setErrorLayoutChildClickIds(@LayoutRes int... errorClickViewIds) {
            this.errorClickViewIds = errorClickViewIds;
            return this;
        }

        /**
         * 设置出错布局
         *
         * @param errorLayoutResId 出错布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setErrorLayout(@LayoutRes int errorLayoutResId) {
            this.errorLayoutID = errorLayoutResId;
            return this;
        }

        /**
         * 设置出错布局
         *
         * @param errorLayout 出错布局
         * @return 状态布局 Build 对象
         */
        public Builder setErrorLayout(@NonNull View errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        ///////////////////////////////////////////
        /////////////////无网络布局////////////////////
        ///////////////////////////////////////////

        /**
         * 设置无网络布局子view点击事件
         *
         * @param noNetworkClickViewIds 无网络布局需要点击事件的 ID
         * @return 状态布局 Build 对象
         */
        public Builder setNoNetworkLayoutChildClickIds(@LayoutRes int... noNetworkClickViewIds) {
            this.noNetworkClickViewIds = noNetworkClickViewIds;
            return this;
        }

        /**
         * 设置无网络布局
         *
         * @param noNetWorkLayoutId 无网络布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setNoNetworkLayoutID(@LayoutRes int noNetWorkLayoutId) {
            this.noNetWorkLayoutID = noNetWorkLayoutId;
            return this;
        }

        /**
         * 设置无网络布局
         *
         * @param noNetWorkLayout 无网络布局
         * @return 状态布局 Build 对象
         */
        public Builder setNoNetWorkLayout(@NonNull View noNetWorkLayout) {
            this.noNetWorkLayout = noNetWorkLayout;
            return this;
        }

        /**
         * 设置默认布局的背景颜色，包括加载中、空数据和出错布局
         *
         * @param defaultBackgroundColor 默认布局的背景颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLayoutsBackgroundColor(int defaultBackgroundColor) {
            this.defaultBackgroundColor = defaultBackgroundColor;
            return this;
        }


        /**
         * 设置点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnStatusLayoutClickListener(OnStatusLayoutClickListener listener) {
            this.onStatusLayoutClickListener = listener;
            return this;
        }

        /**
         * 设置子view点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnStatusLayoutClickChildListener(OnStatusLayoutClickChildListener listener) {
            this.onStatusLayoutClickChildListener = listener;
            return this;
        }

        /**
         * 创建状态布局管理器
         *
         * @return 状态布局管理器
         */
        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }

}
