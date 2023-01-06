# ImmutablePendingIntent

## Overview

ImmutablePendingIntent provides

- **methods** that create immutability-safe PendingIntent
- **lint** to make an error when using methods that create PendingIntent
- **quick fix** that fixes to not use methods that create PendingIntent

## Why need ImmutablePendingIntent

Apps targeting Android 12 and higher must specify either `FLAG_IMMUTABLE` or `FLAG_MUTABLE` when constructing a PendingIntent. More
info: https://developer.android.com/about/versions/12/behavior-changes-12#pending-intent-mutability

### methods

PendingIntent creation methods are unsafe because it is not clear whether the flags parameter specify either `FLAG_IMMUTABLE` or `FLAG_MUTABLE` flag or not.

`ImmutablePendingIntent` provides `ImmutablePendingIntent.get~` and `MutablePendingIntent.get~` methods that its flags is clearly either `FLAG_IMMUTABLE` or `FLAG_MUTABLE`.

### lint

`UnspecifiedImmutableFlag` lint detects that create PendingIntent not specifying either `FLAG_IMMUTABLE` or `FLAG_MUTABLE` flag. But, `UnspecifiedImmutableFlag` lint cannot detect the flags passed as
method parameter.

`ImmutablePendingIntent` provides lint to make an error when using methods that create PendingIntent.

### quick fix

Quick fix doesn't provides by default.

`ImmutablePendingIntent` provides a quick fix that fixes to use either `ImmutablePendingIntent.get~` or `MutablePendingIntent.get~` methods.

### Can you remember that you must specify either `FLAG_IMMUTABLE` or `FLAG_MUTABLE` to PendingIntent?

You can do now. But, a year later? Can your team member do? If you use `ImmutablePendingIntent`, you don't need to remember.

When using PendingIntent, PendingIntent creating methods occurs error by lint. You can fix it by quick fix.

### Can you remember why you specified `FLAG_MUTABLE` to PendingIntent?

You can do now. But, a year later? Can your team member do? If you use `ImmutablePendingIntent`, you don't need to remember.

When using `MutablePendingIntent`, you should explain why to use `MutablePendingIntent` by specifying UseMutablePendingIntent annotation.

## Usage

### Dependency

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.wada811.immutablependingintent/immutablependingintent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.wada811.immutablependingintent/immutablependingintent)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.wada811.immutablependingintent:immutablependingintent:x.y.z'
}
```

### Quick Fix

1. Hover your `PendingIntent.get~` method.
2. Select either from two quick fixes below.
    - Replace with ImmutablePendingIntent.get~
    - Replace with MutablePendingIntent.get~

![UseRawPendingIntentQuickFix](./docs/UseRawPendingIntentQuickFix.png?raw=true)

#### How to select immutable or mutable

See [Create immutable pending intents whenever possible | Intents and Intent Filters | Android Developers](https://developer.android.com/guide/components/intents-filters#CreateImmutablePendingIntents)

#### When using `MutablePendingIntent`, you should specify `UseMutablePendingIntent` annotation

In most cases, your app should create immutable PendingIntent. However, certain use cases require mutable PendingIntent.

When using `MutablePendingIntent` without specifying `UseMutablePendingIntent` annotation, lint makes an error.

![UseMutablePendingIntentQuickFix](./docs/UseMutablePendingIntentQuickFix.png?raw=true)

You should explain why to use `MutablePendingIntent` by specifying `UseMutablePendingIntent` annotation.

```kotlin
@UseMutablePendingIntent(reason = UseMutablePendingIntent.Reason.YouMustSelectReason)
fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
    return MutablePendingIntent.getActivity(context, requestCode, intent, flags)
}
```
