package net.mostlyoriginal.api.component.graphics;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

	@Test
	public void should_convert_hex1_to_r()
	{
		Color color = new Color("FF000000");
		assertColor(color, 1f, 0f, 0f, 0f);
	}

	@Test
	public void should_convert_hex2_to_g()
	{
		Color color = new Color("00FF0000");
		assertColor(color, 0f, 1f, 0f, 0f);
	}

	@Test
	public void should_convert_hex3_to_b()
	{
		Color color = new Color("0000FF00");
		assertColor(color, 0f, 0f, 1f, 0f);
	}

	@Test
	public void should_convert_hex4_to_a()
	{
		Color color = new Color("000000FF");
		assertColor(color, 0f, 0f, 0f, 1f);
	}

	protected void assertColor(Color color, float r, float g, float b, float a) {
		Assert.assertEquals(r, color.r, 0.01f);
		Assert.assertEquals(g, color.g, 0.01f);
		Assert.assertEquals(b, color.b, 0.01f);
		Assert.assertEquals(a, color.a, 0.01f);
	}
}