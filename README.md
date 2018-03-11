# Expandable CardView
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Download](https://api.bintray.com/packages/theluckycoder/expandablecardview/expandable-cardview/images/download.svg) ](https://bintray.com/theluckycoder/expandablecardview/expandable-cardview/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/vipulasri/Timeline-View/blob/master/LICENSE)

## Sample Project

For an example: checkout [Sample App Code](https://github.com/TheLuckyCoder/Expandable-Card-View/tree/master/sample/src/main) in repository.

## Quick Setup

### 1. Include library

**Using Gradle**

```gradle
dependencies {
    compile 'net.theluckycoder.expandablecardview:expandablecardview:1.1.0'
}
```

**Using Maven**

```maven
<dependency>
  <groupId>net.theluckycoder.expandablecardview</groupId>
  <artifactId>expandablecardview</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```

### 2. Usage
 * In XML Layout:
```xml
<net.theluckycoder.expandablecardview.ExpandableCardView
    android:id="@+id/card"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    android:layout_marginBottom="8dp"
    android:layout_marginLeft="16dp"
    android:layout_marginRight="16dp"
    app:title="Card Title"
    app:description="Card Description" />
```

* In Java Code:
```java
ExpandableCardView card = (ExpandableCardView) findViewById(R.id.card);
// You will this need to enable the transform animation:
card.setExpandCollapseListener(findViewById(android.R.id.content));
```

## Documentation
You can find all the usable methods documented [here](https://github.com/TheLuckyCoder/Expandable-Card-View/blob/master/library/src/main/java/net/theluckycoder/expandablecardview/ExpandableCardView.kt)

## License

```
Copyright 2018 The Lucky Coder

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
