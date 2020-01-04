package arachne.lib.sequences;

public abstract class HostAction extends Action
{
	public HostAction(HostAction host) {
		super(host);
	}
	
	@Override
	public void interrupt() {
		interruptChildren();
		super.interrupt();
	}
	
	@Override
	protected void handleInterruptFromHost() {
		interruptChildren();
		super.handleInterruptFromHost();
	}
	
	protected abstract void interruptChildren();
	protected abstract void handleInterruptFromChild(Action interruptSource);
}
