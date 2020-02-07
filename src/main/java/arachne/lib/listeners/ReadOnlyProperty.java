package arachne.lib.listeners;

import arachne.lib.io.Gettable;

public interface ReadOnlyProperty<T> extends Gettable<T>, Hook<T> { /* Intersection interface */ }
