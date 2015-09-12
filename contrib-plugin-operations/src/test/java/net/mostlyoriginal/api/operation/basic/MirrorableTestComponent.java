package net.mostlyoriginal.api.operation.basic;

import com.artemis.Component;
import net.mostlyoriginal.api.component.common.Mirrorable;

/**
 * @author Daan van Yperen
 */
public class MirrorableTestComponent extends Component implements Mirrorable<MirrorableTestComponent> {

	public int val;

	public MirrorableTestComponent() {
	}

	public MirrorableTestComponent(int val) {
		this.val = val;
	}

	@Override
	public MirrorableTestComponent set(MirrorableTestComponent monkey) {
		this.val = monkey.val;
		return this;
	}
}
