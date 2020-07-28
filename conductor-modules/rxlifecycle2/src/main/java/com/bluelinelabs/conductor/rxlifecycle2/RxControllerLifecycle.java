package com.bluelinelabs.conductor.rxlifecycle2;

import androidx.annotation.NonNull;

import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.OutsideLifecycleException;
import com.trello.rxlifecycle4.RxLifecycle;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

public class RxControllerLifecycle {

    /**
     * Binds the given source to a Controller lifecycle. This is the Controller version of
     * {@link com.trello.rxlifecycle4.android.RxLifecycleAndroid#bindFragment(Observable)}.
     *
     * @param lifecycle the lifecycle sequence of a Controller
     * @return a reusable {@link io.reactivex.rxjava3.core.ObservableTransformer} that unsubscribes the source during the Controller lifecycle
     */
    public static <T> LifecycleTransformer<T> bindController(@NonNull final Observable<ControllerEvent> lifecycle) {
        return RxLifecycle.bind(lifecycle, CONTROLLER_LIFECYCLE);
    }

    private static final Function<ControllerEvent, ControllerEvent> CONTROLLER_LIFECYCLE =
        new Function<ControllerEvent, ControllerEvent>() {
            @Override
            public ControllerEvent apply(ControllerEvent lastEvent) {
                switch (lastEvent) {
                    case CREATE:
                        return ControllerEvent.DESTROY;
                    case CONTEXT_AVAILABLE:
                        return ControllerEvent.CONTEXT_UNAVAILABLE;
                    case ATTACH:
                        return ControllerEvent.DETACH;
                    case CREATE_VIEW:
                        return ControllerEvent.DESTROY_VIEW;
                    case DETACH:
                        return ControllerEvent.DESTROY;
                    default:
                        throw new OutsideLifecycleException("Cannot bind to Controller lifecycle when outside of it.");
                }
            }
        };
}
