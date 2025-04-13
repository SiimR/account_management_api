repositories {
    mavenCentral()
}

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.13.1"
}

allprojects {

    layout.buildDirectory.set(rootProject.layout.projectDirectory.dir("build/${project.name}"))

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    apply(plugin = "java-library")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    dependencies {
        implementation("org.mapstruct:mapstruct:1.6.3")
        implementation("org.springframework:spring-context:6.2.5")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

subprojects {
    tasks.jar {
        enabled = true
    }

    tasks.bootJar {
        enabled = false
    }

    tasks.bootRun {
        enabled = false
    }

    tasks.bootBuildImage {
        enabled = false
    }
}

dependencies {
    implementation(project(":app"))
}

springBoot {
    mainClass.set("com.example.App")
}

tasks.named<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

