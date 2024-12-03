plugins {
    kotlin("jvm") version "2.0.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("com.google.guava:guava:33.3.1-jre")

    implementation("commons-io:commons-io:2.18.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.commons:commons-collections4:4.5.0-M2")
    implementation("org.apache.commons:commons-text:1.12.0")
    implementation("org.apache.commons:commons-csv:1.12.0")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("com.github.ben-manes.caffeine:guava:3.1.8")
    implementation("com.github.ben-manes.caffeine:jcache:3.1.8")
}

tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}
