package com.yptake.yplearnproject.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yptake.yplearnproject.di.module.MusicModule;
import com.yptake.yplearnproject.mvp.contract.MusicContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yptake.yplearnproject.mvp.ui.fragment.MusicFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2020 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MusicModule.class, dependencies = AppComponent.class)
public interface MusicComponent {
    void inject(MusicFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MusicComponent.Builder view(MusicContract.View view);

        MusicComponent.Builder appComponent(AppComponent appComponent);

        MusicComponent build();
    }
}