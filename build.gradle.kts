import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.spongepowered.gradle.plugin") version "2.1.1"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

dependencies {
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.5")

    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")

    testImplementation(kotlin("test"))
}
repositories {
    mavenCentral()
}

sponge {
    apiVersion("8.2.0-SNAPSHOT")
    license("CHANGEME")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("zoddjoin") {
        displayName("ZoddJoin")
        entrypoint("me.zodd.zoddJoin.ZoddJoin")
        description("A simple Join Message Plugin")
        version("0.2.0")
        links {
            homepage("https://ore.spongepowered.org/DrZoddiak/ZoddJoin")
            source("https://github.com/DrZoddiak/ZoddJoin")
            issues("https://github.com/DrZoddiak/ZoddJoin/issues")
        }
        contributor("DrZodd") {
            description("Lead Developer")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
        dependency("kruntime") {
            version("0.4.0")
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

tasks.shadowJar {
    exclude("kotlin/**")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
    }
}

tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}