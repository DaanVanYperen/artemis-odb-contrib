# Changelog

## Version: 1.0.0

- Build against artemis-odb 0.13.0, LibGDX 1.6.5
- [ODB plugin API](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Plugin)
  - [Smart Component Mapper Plugin](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Extended-Component-Mappers) - Alter components with less code. `all platforms`
  - [Profiler Plugin](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Profiler-Plugin) - profile system performance. `all platforms` `requires libgdx`
  - [Operations Plugin](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Operations-Plugin) - Scheduled operations on components and entities. Like LibGDX actions. `all platforms` `requires libgdx`
- Core
  - Systems & Managers
    - [`PacedProcessingSystem`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Spread-System) - Optimally spread out entity operations.
    - [`TimeboxedProcessingSystem`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Timeboxed-System) - Processes entities until time runs out. Continues where it left off next invocation.
    - [`DualEntityProcessingSystem`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Dual-Entity-Processing-System) - Process two sets of entities against each other. Good for prototyping things like   
    - [`DualEntitySystem`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Dual-Entity-System)
    - [`AssetManager`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Asset-Manager) - Abstract to implement asset reference component pattern.
collision.
  - Utils
    - [`WorldConfigurationBuilder`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/WorldConfigurationBuilder) - Convenience var-arg addition of systems, managers. Supports plugins.
    - [`Duration`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Duration) - `Operation` related time utilities.
    - [`Preconditions`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Preconditions) - Like guava `checkNotNull`, `checkArgument`.
    - Quad Tree.
  - Interfaces
    - [`Mirrorable`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Mirrorable) - Mirror component state to another.
    - [`Tweenable`](https://github.com/DaanVanYperen/artemis-odb-contrib/wiki/Tweenable) - To enable tweening between two component states.
- Jam
  - Refactored many components.
    - Implement mirrorable, tweenable.
    - Use gdx types to wrap color, coordinates.
    - Class name changes.
  - New components: `Label`, `Scale`.
  - `Schedule` converted to fully pooled operations plugin.
  - `EntityEditor` for extendable entity assembly/mutation.
  
## Version: 0.10.2

- artemis-odb 0.10.2

## Version: 0.10.1

- artemis-odb 0.10.1, LibGDX 1.6.4

## Version: 0.9.1

- New polling event dispatcher.
- artemis-odb 0.9.1-SNAPSHOT, libGDX 1.6.1
- Event benchmarking.
- Moved all example components and systems to contrib-jam module.

## Version: 0.7

- Drop in Eventbus.
- Drop in Deferred subsystems: delegate order of entity processing of multiple subsystems to an overarching system.

The contrib-components and contrib-components-libgdx pending some major refactoring.
