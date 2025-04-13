plugins {
    id("org.liquibase.gradle") version "2.2.0"
    distribution
}

dependencies {
    implementation("org.liquibase:liquibase-core:4.31.1")
    liquibaseRuntime("org.liquibase:liquibase-core:4.31.1")
    liquibaseRuntime("com.h2database:h2:2.3.232")
    liquibaseRuntime("org.yaml:snakeyaml:2.4")
    liquibaseRuntime("info.picocli:picocli:4.7.6")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changelogFile" to "/changelog/changelog.yaml",
            "searchPath" to "$projectDir/src/main/resources",
            "url" to "jdbc:h2:tcp://localhost:9090/mem:account_management",
            "username" to "sa",
            "password" to "password",
            "logLevel" to "info",
            "defaultSchemaName" to "accounts"
        )
    }
}
distributions {
    main {
        contents {
            from(".")
            include("changelog/**")
            into("/")
        }
    }
}