pluginManagement {
    repositories {
        google() // Necessário para plugins Android
        mavenCentral() // Repositório Maven
        gradlePluginPortal() // Plugins de terceiros, se necessário
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NomeDoProjeto"
include(":app")
