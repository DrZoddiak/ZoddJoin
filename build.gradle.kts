import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("zoddlib.project-conventions")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

dependencies {
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")

    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")

    testImplementation(kotlin("test"))
}

sponge {
    plugin("zoddjoin") {
        displayName("ZoddJoin")
        entrypoint("me.zodd.zoddJoin.ZoddJoin")
        description("A simple Join Message Plugin")
        version("0.1.2")
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
    }
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    this.sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}