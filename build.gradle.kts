import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("org.jetbrains.dokka") version "1.7.20"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("maven-publish")
}

group = "io.lexadiky"
version = "0.0.2"

repositories {
    mavenCentral()
}

val ktorVersion = "2.1.2"

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xcontext-receivers"
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
}

publishing {
    publications {
        create<MavenPublication>("pokeapi") {
            groupId = "com.github.lexadiky.pokeapi"
            artifactId = "core"
            version = "1.0"
            from(components["java"])
        }
    }
}
