rootProject.name = "gradleLearning"
include(
    "app-common",
    "app",
    "app:inner-app",
    "app:test-app"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")