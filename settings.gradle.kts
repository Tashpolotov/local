pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pdd_New_project_my"
include(":app")
include(":register:register_domain")
include(":register:register_data")
include(":register:reigster_presentation")
include(":profile:profile_domain")
include(":profile:profile_data")
include(":main:main_domain")
include(":main:main_data")
include(":main:main_presentation")
include(":core:core_utils")
include(":video:video_domain")
include(":video:video_data")
include(":video:video_presentation")
include(":rank:rank_domain")
include(":rank:rang_data")
include(":rank:rank_presentation")
include(":local:local_helper")
include(":profile:profile_presenter")
