package arachne.lib.sequences;

public abstract class ActionWrapper extends HostAction
{
	protected Action currentAction;
	
	protected abstract Action getNextAction(boolean isFirst);
	
	public ActionWrapper(HostAction host) {
		super(host);
	}

	@Override
	protected void initialize() {
		currentAction = getNextAction(true);
	}
	
	@Override
	protected void execute() {
		ActionState subState = null;
		
		while(currentAction != null && (subState = currentAction.act()) == ActionState.FINISHED) {
			currentAction = getNextAction(false);
		}
		
		if(subState == ActionState.INTERRUPTED) state = ActionState.INTERRUPTED;
	}
	
	@Override
	protected boolean isFinished() {
		return currentAction == null;
	}
	
	@Override
	protected void interruptChildren() {
		if(currentAction != null) currentAction.handleInterruptFromHost();
	}
	
	@Override
	protected void handleInterruptFromChild(Action interruptSource) {
		interruptSelf();
		interruptHost();
	}
}
