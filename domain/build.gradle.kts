plugins {
    kotlin("jvm")
}


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

}
dependencies {
    api(libs.javax.inject)
    api(libs.coroutines.core)
}