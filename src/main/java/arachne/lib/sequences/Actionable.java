package arachne.lib.sequences;

@FunctionalInterface
public interface Actionable
{
	Action asAction(HostAction host);
}
