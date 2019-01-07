artemis-odb-contrib
===================

[![Build Status](https://travis-ci.org/DaanVanYperen/artemis-odb-contrib.svg)](https://travis-ci.org/DaanVanYperen/artemis-odb-contrib)

Drop-in extensions for artemis-odb. Event bus, scheduled operations, deferred systems, profiler, abstract and prefab
systems, components and networking. Well, eventually anyway.

### When to use this
If you want to quickly prototype a game without getting bogged down by the
details of entity component systems, this package will help get you started.

I use this toolkit for jam games. You are welcome to use it for whatever you need!

Alternatively, if you want to properly set up artemis-odb + libgdx or playn 
with all fancy features, check out these instead:
- [libgdx-artemis-quickstart](https://github.com/DaanVanYperen/libgdx-artemis-quickstart)
- [playn-artemis-quickstart](https://github.com/DaanVanYperen/playn-artemis-quickstart)

### Library Versions
Artemis-odb 2.2.0, (Optional) LibGDX 1.9.9.

### License
The primary license for this code is MIT. 
Some stubs from LibGDX are licensed under Apache 2.0.

### Download

#### Maven

```xml
<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-core</artifactId>
  <version>2.3.0</version>
</dependency>

<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-eventbus</artifactId>
  <version>2.3.0</version>
</dependency>
```

#### Gradle

```groovy
dependencies { 
    compile "net.mostlyoriginal.artemis-odb:contrib-core:2.3.0"
    compile "net.mostlyoriginal.artemis-odb:contrib-eventbus:2.3.0"
}
```
