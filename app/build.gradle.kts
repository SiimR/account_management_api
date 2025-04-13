plugins {
    java
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.4")

    implementation(project(":domain"))
    implementation(project(":adapter:web"))
    implementation(project(":adapter:jdbc"))
    implementation(project(":liquibase"))
}

springBoot {
    mainClass = "com.example.App"
}
