/*
 * This file was auto-generated by Arachne's Template Manager.
 * Edits made to this file will be overridden on regeneration.
 *
 * Template file: Sink.jgn
 * Variable definitions: Sink.json
 * Label used: Generic
 */
package arachne.lib.pipeline;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import arachne.lib.io.Gettable;
import arachne.lib.io.Settable;
import arachne.lib.logging.ArachneLogger;

/**
 * A Sink holds a value. When the Sink accepts a new value, that value may be
 * tested to match a {@link Predicate} filter then modified using a
 * {@link UnaryOperator}, and the resultant value becomes the new value of the Sink.
 * 
 * @param <T> The type of the value held by the Sink.
 *
 * @author Sean Zammit
 */
public class Sink<T> implements Gettable<T>, Settable<T>
{
	protected T value;

	protected Predicate<T> filter;
	protected UnaryOperator<T> modifier;

	/**
	 * Constructor for a Sink.
	 *
	 * @param initialValue The initial value held by the Sink.
	 */
	public Sink(T initialValue) {
		this.value = initialValue;

		this.filter = tautology();
		this.modifier = UnaryOperator.identity();
	}

	/**
	 * Get the current value held by the Sink.
	 */
	@Override
	public T get() {
		return value;
	}

	/**
	 * Accept a new value. If the new value passes the Sink's filter, then it
	 * will be passed through the modifier and the Sink's value will be changed
	 * to the result.
	 */
	@Override
	public void accept(T value) {
		if(filter.test(value)) overwriteValue(modifier.apply(value));
	}

	/**
	 * Overwrite the value stored in the Sink.
	 *
	 * @param value The new value to store.
	 *
	 * @return The previous value that was stored.
	 */
	protected T overwriteValue(T value) {
		T oldValue = this.value;
		this.value = value;

		return oldValue;
	}

	/**
	 * Set the modifier to be applied to values accepted by the Sink.
	 *
	 * @param modifier The modifier to apply. Should not be null.
	 *
	 * @return The modifier applied.
	 */
	public Sink<T> setModifier(UnaryOperator<T> modifier) {
		if(modifier == null) {
			ArachneLogger.getInstance().error("Tried to set Sink modifier to null... changing to identity instead");

			this.modifier = UnaryOperator.identity();
			return this;
		}

		this.modifier = modifier;
		return this;
	}

	/**
	 * Clear the modifier applied to values accepted by the Sink.
	 */
	public void clearModifier() {
		this.modifier = UnaryOperator.identity();
	}

	/**
	 * Set the filter used to test values accepted by the Sink.
	 *
	 * @param filter The filter to apply. Should not be null.
	 *
	 * @return The filter applied.
	 */
	public Sink<T> setFilter(Predicate<T> filter) {
		if(filter == null) {
			ArachneLogger.getInstance().error("Tried to set Sink filter to null... changing to tautology instead");

			this.filter = tautology();
			return this;
		}

		this.filter = filter;
		return this;
	}

	/**
	 * Clear the filter testing values accepted by the Sink.
	 */
	public void clearFilter() {
		this.filter = tautology();
	}

	/**
	 * Static helper function to return a tautology for filtering.
	 *
	 * @param <T> The type of values tested by the Predicate.
	 *
	 * @return A Predicate that always returns true.
	 */
	protected static <T> Predicate<T> tautology() {
		return (value) -> true;
	}
}
