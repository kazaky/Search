# Rolemodel
Rolemodel app to be used as a base arch for a modern Android production app

- The app is 100% [Kotlin](http://kotlinlang.org) with MVVM Architecture
- I use [`Koin`](https://insert-koin.io) which is a lightweight dependency injection framework to manage all dependencies. And [`RxJava`](http://reactivex.io/) for asynchronous work. I also use [`Retrofit`](https://square.github.io/retrofit/).
- Layers `DataRepository`,`ViewModel`,`View`
I'm using a simple arch of MVVM to achieve better separation of concerns, testability.

# My approch
#### Simple
Simplicity of the codebase with Kotlin, the lightweight dependency injection framework Koin, simple structure, minimalist maintainable code approach.

#### Extensible 
Extensible for adding more features easily by hiding some of the logic in BaseClasses, also adding new modules Koin is easier than ever.
#### Testable
Testability with DI, it's easier to swap dependencies, mock and test ViewModels.
#### Error prone
Error prone with Kotlin, RxJava onError and a base class for handling all possible exceptions, using a single view state with liveData to hold and survive orientation changes.
#### Good UI/UX
Showing meaningful error messages and tells the user to retry on errors.

## TODO
* Do performance checks with LeakCanary and Android profiler.
* Use Koin DI in testing module.
* Add UI Espresso tests.

Build the Project
-------------------

Compile the project with Gradle using
```sh
./gradlew build
```

Showcase [VIDEO]
-----------
[![](http://img.youtube.com/vi/ASbIQCFQYc4/0.jpg)](http://www.youtube.com/watch?v=ASbIQCFQYc4 "")

Libraries used
--------------
* [Foundation][0] - Components for core system capabilities, and Kotlin extensions.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code and helper functions for easy integration with coroutines.
* [Architecture][3] - A collection of libraries that help design robust, testable, and maintainable apps.
  * [Lifecycles][4] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][5] - Build data objects that notify views when the underlying data changes.
  * [ViewModel][6] - Store UI-related data that isn't destroyed on app rotations.
* Third party
  * [Glide][7] for image loading.
  * [Koin][8] a lightweight dependency injection framework to manage all dependencies.
  * [Retrofit][9] a type-safe HTTP client for Android and Java.
  * [LoggingInterceptor][10] to log web requests in a pretty way in logcat.
  * [RxJava][11] for managing background threads.
  * [Leakcanary][12] for tracing memory leaks.

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[3]: https://developer.android.com/jetpack/arch/
[4]: https://developer.android.com/topic/libraries/architecture/lifecycle
[5]: https://developer.android.com/topic/libraries/architecture/livedata
[6]: https://developer.android.com/topic/libraries/architecture/viewmodel
[7]: https://bumptech.github.io/glide/
[8]: https://insert-koin.io
[9]: https://square.github.io/retrofit/
[10]: https://github.com/ihsanbal/LoggingInterceptor
[11]: http://reactivex.io/
[12]: https://square.github.io/leakcanary/
