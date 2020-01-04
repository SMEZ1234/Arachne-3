package arachne.lib.hardware;

import edu.wpi.first.wpilibj.DriverStation;

public class ButtonPanel
{
	protected final DriverStation driverStation;
	protected final int port;
	
	public ButtonPanel(int port) {
		driverStation = DriverStation.getInstance();
		this.port = port;
	}
	
	protected boolean getButton(int button) {
		return driverStation.getStickButton(port, button);
	}
}
