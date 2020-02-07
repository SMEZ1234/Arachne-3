package arachne.lib;

import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.ActionConductor;
import arachne.lib.sequences.Actionable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoManager<AutoT extends Enum<?> & Actionable> implements Schedulable
{
	protected static final String DASHBOARD_TAB = "Autonomous";
	protected static final String SELECTION_KEY = "Auto selection";
	
	protected final ActionConductor conductor;
	protected final SendableChooser<AutoT> autoChooser;
	
	public AutoManager(AutoT defaultAuto, AutoT[] autos) {
		this.conductor = new ActionConductor();
		
		this.autoChooser = new SendableChooser<AutoT>();
		this.autoChooser.setDefaultOption(defaultAuto.toString(), defaultAuto);
		
		for(AutoT auto : autos) {
			if(auto != defaultAuto) autoChooser.addOption(auto.toString(), auto);
		}

		Shuffleboard.getTab(DASHBOARD_TAB).add(SELECTION_KEY, autoChooser);
	}
	
	public void startAuto() {
		startAuto(autoChooser.getSelected());
	}
	
	public void startAuto(AutoT auto) {
		stopAuto();
		conductor.add(auto);
	}
	
	public void stopAuto() {
		conductor.interrupt();
	}
	
	public boolean isRunning() {
		return conductor.hasActions();
	}

	@Override
	public void run() {
		conductor.run();
	}
}
