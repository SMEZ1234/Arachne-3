package arachne.lib.requirements;

import arachne.lib.logging.ArachneLogger;

public abstract class AbstractRequirementManager<UserT> implements RequirementManager<UserT>
{
	protected UserT currentUser;

	@Override
	public boolean isAcquirable() {
		return currentUser == null;
	}

	@Override
	public void acquire(UserT user) {
		if(currentUser != null) interrupt(currentUser);
		currentUser = user;
	}

	@Override
	public void release(UserT user) {
		if(currentUser == user) currentUser = null;
		else ArachneLogger.getInstance().warn("Requirement for type " + user.getClass().getName() + " released, but not owned by releasing object");
	}
}
