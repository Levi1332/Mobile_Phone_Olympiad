buildscript {
    repositories {
        google() // Добавьте эту строку, если вы еще не добавили репозиторий Google Maven
        // Другие репозитории, если они вам нужны
    }
    dependencies {
        classpath(libs.gradle) // Проверьте, что libs.gradle корректно определена где-то в вашем скрипте
        classpath("com.google.gms:google-services:4.4.1") // Здесь добавляется зависимость для Google Services Plugin
        classpath(libs.firebase.crashlytics.gradle) // Проверьте, что libs.firebase.crashlytics.gradle корректно определена где-то в вашем скрипте
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.gms.google-services") version "4.4.1" apply false // Добавление зависимости для Google Services Gradle plugin
    alias(libs.plugins.androidApplication) apply false
}
