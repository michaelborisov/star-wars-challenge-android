# StarWars challenge Android.


## Description:
This app connects to the StarWars API (https://swapi.co/) and requests information about StarWars Universe
according to user needs. The workflow is as follows: user lands on the character search screen and can
enter the name of the desired character. When the character is found - user can click on it and will see character details
(language, homeworld, films and etc...)

## Architecture:
App follows Model-View-Presenter-View-Model (MVPVM) architecture. For this purpose following libraries were used:
- ThirtyInch library (https://github.com/grandcentrix/ThirtyInch).
- ViewModel (https://developer.android.com/topic/libraries/architecture/viewmodel). 
This architecture favors the stateful Presenter and dumb View pattern.

While developing, I was following SOLID principles (https://en.wikipedia.org/wiki/SOLID) and ideas, described in
the book "Clean Architecture". (http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## 3rd party libraries:
- Dagger2 (https://google.github.io/dagger/) is used for dependency injection.
- Retrofit (https://square.github.io/retrofit/) is used for making internet requests.
- RxJava2 and RxBindings are used to make the application run smooth.
- Kakao (https://github.com/agoda-com/Kakao) is used for writing more readable Android Tests.


## Tests:
Both unit tests and androidTests can be found in the project.