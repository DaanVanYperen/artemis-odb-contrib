artemis-odb-contrib
===================

[![Build Status](https://travis-ci.org/DaanVanYperen/artemis-odb-contrib.svg)](https://travis-ci.org/DaanVanYperen/artemis-odb-contrib)

Drop-in extensions for artemis-odb. Event bus, scheduled operations, deferred systems, profiler, abstract and prefab
systems, plugin api, components and networking. Well, eventually anyway.

### When to use this
If you want to quickly prototype a game without getting bogged down by the
details of entity component systems, this package will help get you started.

I use this as my toolkit for jam games. You are welcome to use it for whatever you need!

Alternatively, if you want to properly set up artemis-odb + libgdx or playn 
with gwt support, check out these instead:
- [libgdx-artemis-quickstart](https://github.com/DaanVanYperen/libgdx-artemis-quickstart)
- [playn-artemis-quickstart](https://github.com/DaanVanYperen/playn-artemis-quickstart)

### Library Versions
Artemis-odb 0.12.1, (Optional) LibGDX 1.6.5

### License
The primary license for this code is CC0.

### Download

#### Maven

```xml
<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-core</artifactId>
  <version>1.0.0</version>
</dependency>

<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-eventbus</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### Gradle

```groovy
dependencies { 
    compile "net.mostlyoriginal.artemis-odb:contrib-core:1.0.0"
    compile "net.mostlyoriginal.artemis-odb:contrib-eventbus:1.0.0"
}
```
