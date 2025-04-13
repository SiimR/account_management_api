plugins {
    id("java")
}

group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(project(":app"))
    testImplementation(project(":adapter:jdbc"))
    testImplementation(project(":adapter:web"))
    testImplementation("org.springframework.boot:spring-boot-starter-web:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")
}

tasks.test {
    useJUnitPlatform()
}
