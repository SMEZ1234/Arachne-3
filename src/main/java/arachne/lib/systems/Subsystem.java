package arachne.lib.systems;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

import arachne.lib.requirements.AbstractRequirementManager;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.Action;
import arachne.lib.sequences.ActionConductor;

public abstract class Subsystem extends AbstractRequirementManager<Action> implements Schedulable, Tree<Subsystem>
{
	protected Set<Schedulable> bindings;
	protected ActionConductor internalActionScheduler;
	
	protected Subsystem parent;
	protected Set<Subsystem> children;
	
	public Subsystem() {
		this.bindings = new LinkedHashSet<Schedulable>();
		this.internalActionScheduler = addBinding(new ActionConductor());
		
		this.children = new LinkedHashSet<Subsystem>();
	}
	
	// --------------------
	// Factory methods
	// --------------------
	
	public static <T extends Subsystem> T create(Supplier<T> subsystemSupplier) {
		return create(subsystemSupplier, null);
	}
	
	public static <T extends Subsystem> T create(Supplier<T> subsystemSupplier, Subsystem parent) {
		T subsystem = subsystemSupplier.get();
		subsystem.setParent(parent);
		subsystem.initialize();
		
		return subsystem;
	}
	
	protected abstract void initialize();
	
	// --------------------
	// Schedulable methods
	// --------------------
	
	@Override
	public void run() {
		runBindings();
		_run();
	}
	
	protected void runBindings() {
		for(Schedulable binding : bindings) binding.run();
	}
	
	protected void _run() {}
	
	public <BindingT extends Schedulable> BindingT addBinding(BindingT binding) {
		bindings.add(binding);
		return binding;
	}
	
	public void removeBinding(Schedulable binding) {
		bindings.remove(binding);
	}
	
	public void interruptInternalActions() {
		internalActionScheduler.interrupt();
	}
	
	// --------------------
	// Tree methods
	// --------------------

	@Override
	public final Subsystem getSelfAsTree() {
		return this;
	}

	@Override
	public Subsystem getParent() {
		return parent;
	}

	@Override
	public Set<Subsystem> getChildren() {
		return children;
	}

	@Override
	public void setParent(Subsystem parent) {
		if(this.parent != null) this.parent.removeChild(this);
		
		this.parent = parent;
		
		if(this.parent != null) this.parent.addChild(this);
	}
	
	protected void addChild(Subsystem child) {
		children.add(child);
	}
	
	protected void removeChild(Subsystem child) {
		children.remove(child);
	}
	
	// --------------------
	// AbstractRequirementManager methods
	// --------------------
	
	@Override
	public boolean isAcquirable() {
		return !search(OperationFilter.FAMILY_LINE, Subsystem::isBusy);
	}
	
	protected boolean isBusy() {
		return currentUser != null;
	}

	@Override
	public void interrupt(Action user) {
		apply(OperationFilter.FAMILY_LINE, Subsystem::_interrupt);
	}
	
	protected void _interrupt() {
		if(currentUser != null) currentUser.interrupt();
	}
}
