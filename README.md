artemis-odb-contrib
===================

[![Build status](https://github.com/DaanVanYperen/artemis-odb-contrib/actions/workflows/javaCI.yml/badge.svg?label=Build)](https://github.com/DaanVanYperen/artemis-odb-contrib/actions/workflows/javaCI.yml)
[![Discord Chat](https://img.shields.io/discord/348229412858101762?logo=discord)](https://libgdx.com/community/discord/)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](https://opensource.org/licenses/MIT)

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
Artemis-odb 2.4.0, (Optional) LibGDX 1.9.14.

#### License

This work is licensed under MIT License except some small snippets from LibGDX are 
licensed under Apache 2.0. Apache 2.0 license can be found under contrib-core\LICENSE.libgdx.

`SPDX-License-Identifier: MIT AND Apache-2.0`

### Download

#### Maven

```xml
<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-core</artifactId>
  <version>2.4.0</version>
</dependency>

<dependency>
  <groupId>net.mostlyoriginal.artemis-odb</groupId>
  <artifactId>contrib-eventbus</artifactId>
  <version>2.4.0</version>
</dependency>
```

#### Gradle

```groovy
dependencies { 
    compile "net.mostlyoriginal.artemis-odb:contrib-core:2.4.0"
    compile "net.mostlyoriginal.artemis-odb:contrib-eventbus:2.4.0"
}
```
