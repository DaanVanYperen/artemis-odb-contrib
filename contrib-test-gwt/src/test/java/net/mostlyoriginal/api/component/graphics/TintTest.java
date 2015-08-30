package net.mostlyoriginal.api.component.graphics;

import org.junit.Assert;
import org.junit.Test;

public class TintTest {

	@Test
	public void should_convert_hex1_to_r()
	{
		Tint tint = new Tint("FF000000");
		assertColor(tint, 1f, 0f, 0f, 0f);
	}

	@Test
	public void should_convert_hex2_to_g()
	{
		Tint tint = new Tint("00FF0000");
		assertColor(tint, 0f, 1f, 0f, 0f);
	}

	@Test
	public void should_convert_hex3_to_b()
	{
		Tint tint = new Tint("0000FF00");
		assertColor(tint, 0f, 0f, 1f, 0f);
	}

	@Test
	public void should_convert_hex4_to_a()
	{
		Tint tint = new Tint("000000FF");
		assertColor(tint, 0f, 0f, 0f, 1f);
	}

	protected void assertColor(Tint tint, float r, float g, float b, float a) {
		Assert.assertEquals(r, tint.color.r, 0.01f);
		Assert.assertEquals(g, tint.color.g, 0.01f);
		Assert.assertEquals(b, tint.color.b, 0.01f);
		Assert.assertEquals(a, tint.color.a, 0.01f);
	}
}