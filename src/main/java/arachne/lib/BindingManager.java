package arachne.lib;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.RobotBase;

public abstract class BindingManager<RobotT>
{
	public static <BindingManagerT extends BindingManager<RobotT>, RobotT extends RobotBase> BindingManagerT create(RobotT robot, Supplier<BindingManagerT> bindingManagerSupplier) {
		BindingManagerT bindingManager = bindingManagerSupplier.get();
		bindingManager.createBindings(robot);
		
		return bindingManager;
	}

	protected abstract void createBindings(RobotT robot);
}
