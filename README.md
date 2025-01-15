# tymex-home-assignment

#**🚀 Active branch:** develop

**📱 Project Name**
This android Kotlin project is a home assignment done in 1 week with features to fetch user list data and user details from github api


**🌟 Features**
🌐 Fetch user list from Github API (with local handle and network handle).
🎨 Fetch detail user information from Github API.

📂 Project Structure
`.
├── README.md
├── build.gradle.kts
├── core
│   ├── common
│   ├── data
│   ├── domain
│   └── model
├── gradle
│   └── wrapper
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
├── presentation
│   ├── build
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src
├── project-structure.txt
└── settings.gradle.kts`

**🛠️ Tech Stack**
- Programming Language: Kotlin / Java
- Architecture: MVVM (Model-View-ViewModel) + Clean architecture
- Core Libraries
  
Retrofit - API communication.
`api("com.squareup.retrofit2:retrofit:${versionRetrofit}")
api("com.squareup.retrofit2:converter-gson:${versionRetrofit}")
api("com.squareup.retrofit2:converter-moshi:${versionRetrofit}")
api("com.squareup.okhttp3:okhttp:${versionOkhttp}")
api("com.squareup.okhttp3:logging-interceptor:${versionOkhttp}")`

Room - Local database storage.
`kapt("androidx.room:room-compiler:${versionRoom}")
implementation("androidx.room:room-runtime:${versionRoom}")
implementation("androidx.room:room-ktx:${versionRoom}")`

Hilt - Dependency Injection.
`implementation("com.google.dagger:hilt-android:${versionDagger}")
kapt("com.google.dagger:hilt-android-compiler:${versionDagger}")
implementation("androidx.hilt:hilt-work:${versionHilt}")
kapt("androidx.hilt:hilt-compiler:${versionHilt}")`

Timber - For logging.
`implementation("com.jakewharton.timber:timber:5.0.1")`

UI Libraries
Coil - Image loading and display.
`implementation("io.coil-kt:coil:$versionCoil")
implementation("io.coil-kt:coil-gif:$versionCoil")
implementation("io.coil-kt:coil-video:$versionCoil")`

Material Design Components.
`implementation("com.google.android.material:material:${versionMaterial}")`

Shimmer - Loading animations.
`implementation("com.facebook.shimmer:shimmer:0.5.0")`

**Testing**
JUnit - Unit testing framework.
`testImplementation("junit:junit:4.13.2")`
Mockito - For mocking.
`testImplementation("org.mockito:mockito-core:$versionMockito")
testImplementation("org.mockito.kotlin:mockito-kotlin:$versionMockitoKotlin")`


