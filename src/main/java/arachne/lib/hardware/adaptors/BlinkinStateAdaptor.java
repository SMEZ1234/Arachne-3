package arachne.lib.hardware.adaptors;

import arachne.lib.io.Gettable;
import arachne.lib.io.Settable;
import edu.wpi.first.wpilibj.Spark;

public class BlinkinStateAdaptor implements Gettable<BlinkinStateAdaptor.Pattern>,
		Settable<BlinkinStateAdaptor.Pattern>
{
	public static enum Pattern
    {
        YELLOW(0.57),// HOT_PINK(0.57),
        DARK_RED(0.59),
        RED(0.61),
        HOT_PINK(0.63),// RED_ORANGE(0.63),
        PINK(0.65),// ORANGE(0.65),
        PALE_VIOLET(0.67),// GOLD(0.67),
        VIOLET(0.69),// YELLOW(0.69),
        PURPLE(0.71),// LAWN_GREEN(0.71),
        LIGHT_PURPLE(0.73),// LIME(0.73),
        BLUE_PURPLE(0.75),// DARK_GREEN(0.75),
        DARK_BLUE(0.77),// GREEN(0.77),
        BLUE(0.79),// BLUE_GREEN(0.79),
        LIGHT_BLUE(0.81),// AQUA(0.81),
        PALE_BLUE(0.83),// SKY_BLUE(0.83),
        AQUA(0.85),// DARK_BLUE(0.85),
        TURQUOISE(0.87),// BLUE(0.87),
        GREEN(0.89),// BLUE_VIOLET(0.89),
        LIME(0.91),// VIOLET(0.91),
        WHITE(0.93),
        GRAY(0.95),
        DARK_GRAY(0.97),
        BLACK(0.99);

        protected final double value;

        private Pattern(double value) {
            this.value = value;
        }
    }
	
	protected final Spark controller;
	protected Pattern pattern;
	
	public BlinkinStateAdaptor(int channel) {
		this.controller = new Spark(channel);
	}

	@Override
	public Pattern get() {
		return pattern;
	}

	@Override
	public void accept(Pattern pattern) {
		this.pattern = pattern;
		this.controller.set(pattern.value);
	}
}
