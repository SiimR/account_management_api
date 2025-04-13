plugins {
    id("java")
}

group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.4")
    implementation("org.springframework:spring-web:6.2.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
}

tasks.jar {
    archiveFileName.set("account_management_api-web.jar")
}