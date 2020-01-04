package arachne.lib;

import java.util.function.Supplier;

public abstract class HardwareManager
{
	public static <T extends HardwareManager> T create(Supplier<T> hardwareManagerSupplier) {
		T hardwareManager = hardwareManagerSupplier.get();
		hardwareManager.initializeHardware();
		
		return hardwareManager;
	}

	protected abstract void initializeHardware();
}
