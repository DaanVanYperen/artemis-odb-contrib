package net.mostlyoriginal.api.plugin.extendedcomponentmapper;

import com.artemis.World;
import com.artemis.injection.FieldResolver;
import com.artemis.injection.InjectionCache;
import com.artemis.injection.UseInjectionCache;
import com.artemis.utils.reflect.Field;

/**
 * Dependency Injection of extended component mappers.
 *
 * @author Daan van Yperen
 */
public class ExtendedComponentMapperFieldResolver implements FieldResolver, UseInjectionCache {

	private World world;
	private InjectionCache cache;

	@Override
	public void initialize(World world) {
		this.world = world;
	}

	@Override
	public Object resolve(Class<?> fieldType, Field field) {
		if ( fieldType.equals(M.class) ) {
			return getMapper(field);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private M<?> getMapper(Field field) {
		return new M(cache.getGenericType(field), world);
	}


	@Override
	public void setCache(InjectionCache cache) {
		this.cache = cache;
	}
}
