// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    val compose_version by extra("1.0.0-beta09")
    val hilt_version by extra("2.37")
    val timber_version by extra("4.7.1")
    val compose_lifecycle_version by extra("1.0.0-alpha07")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        /**
         * must be 1.5.10
         *
         * [hilt 2.37] + [jetpack compose beta-09] + [kotlin 1.5.20] produces error
         */
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38")
    }

}

