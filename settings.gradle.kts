pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        // maven {
        //     url = uri("https://maven.pkg.github.com/ayseelg/rtmp-live-stream-android")
        //     credentials {
        //         username = "ayseelg"
        //         password = "<GITHUB_TOKEN>"
        //     }
        // }
    }
}

rootProject.name = "RtmpTest"
include(":app")
 