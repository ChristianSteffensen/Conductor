package com.bluelinelabs.conductor.rxlifecycle2;

import android.os.Bundle;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.RestoreViewOnCreateController;
import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public abstract class RxRestoreViewOnCreateController extends RestoreViewOnCreateController implements LifecycleProvider<ControllerEvent> {
    private final BehaviorSubject<ControllerEvent> lifecycleSubject;

    public RxRestoreViewOnCreateController() {
        this(null);
    }

    public RxRestoreViewOnCreateController(Bundle args) {
        super(args);
        lifecycleSubject = ControllerLifecycleSubjectHelper.create(this);
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ControllerEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ControllerEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxControllerLifecycle.bindController(lifecycleSubject);
    }
}
