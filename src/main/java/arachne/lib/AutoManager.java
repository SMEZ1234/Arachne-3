package arachne.lib;

import java.util.function.Function;

import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.ActionConductor;
import arachne.lib.sequences.Actionable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoManager<RobotT, AutoT extends Enum<?> & Function<RobotT, Actionable>> implements Schedulable
{
	protected static final String DASHBOARD_TAB = "Autonomous";
	protected static final String SELECTION_KEY = "Auto selection";
	
	protected final ActionConductor conductor;
	protected final SendableChooser<Actionable> autoChooser;
	
	public AutoManager(RobotT robot, AutoT defaultAuto, AutoT[] autos) {
		this.conductor = new ActionConductor();
		
		this.autoChooser = new SendableChooser<Actionable>();
		this.autoChooser.setDefaultOption(defaultAuto.toString(), defaultAuto.apply(robot));
		
		for(AutoT auto : autos) {
			if(auto != defaultAuto) autoChooser.addOption(auto.toString(), auto.apply(robot));
		}

		Shuffleboard.getTab(DASHBOARD_TAB).add(SELECTION_KEY, autoChooser);
	}
	
	public void startAuto() {
		startAuto(autoChooser.getSelected());
	}
	
	public void startAuto(Actionable auto) {
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
