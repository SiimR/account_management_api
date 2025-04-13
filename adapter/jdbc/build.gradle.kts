plugins {
    id("java")
}

group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework:spring-jdbc:6.2.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")
    implementation("com.h2database:h2:2.3.232")
}

tasks.test {
    useJUnitPlatform()
}