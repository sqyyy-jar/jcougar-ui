plugins {
    java
}

group = "com.github.sqyyy"
version = "0.5.2-alpha"

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}