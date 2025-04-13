rootProject.name = "account_management_api"
include("app")
include("domain")
include("adapter")
include("adapter:web")
findProject(":adapter:web")?.name = "web"
include("adapter:jdbc")
findProject(":adapter:jdbc")?.name = "jdbc"
include("liquibase")
include("integration-tests")
