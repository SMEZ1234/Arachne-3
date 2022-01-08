package arachne.lib.requirements;

public interface RequirementManager<UserT>
{
	boolean isAcquirable();	
	void acquire(UserT user);
	void release(UserT user);
	void interrupt(UserT user);
	
	default boolean request(UserT user) {
		if(!isAcquirable()) return false;
		
		acquire(user);
		return true;
	}
}
