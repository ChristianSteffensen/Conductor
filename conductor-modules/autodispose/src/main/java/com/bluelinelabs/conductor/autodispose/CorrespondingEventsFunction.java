package com.bluelinelabs.conductor.autodispose;


import autodispose2.OutsideScopeException;
import io.reactivex.rxjava3.functions.Function;


/**
 * Based on https://github.com/uber/AutoDispose/blob/master/lifecycle/autodispose-lifecycle/src/main/java/com/uber/autodispose/lifecycle/CorrespondingEventsFunction.java
 *
 * A corresponding events function that acts as a normal {@link Function} but ensures ControllerEvent event
 * types are used in the generic and tightens the possible exception thrown to {@link OutsideScopeException}.
 */
public interface CorrespondingEventsFunction extends Function<ControllerEvent, ControllerEvent> {

    /**
     * Given an event {@code event}, returns the next corresponding event that this lifecycle should
     * dispose on.
     *
     * @param event the source or start event.
     * @return the target event that should signal disposal.
     * @throws OutsideScopeException if the lifecycle exceeds its scope boundaries.
     */
    @Override ControllerEvent apply(ControllerEvent event) throws OutsideScopeException;
}