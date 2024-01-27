plugins {
    kotlin("jvm")
    id("java-library")
    id("maven-publish")
    id("com.github.jmongard.git-semver-plugin")
}

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/MDL-Viewer/hott-model")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:_")
    implementation("org.apache.httpcomponents:fluent-hc:_")
    api("de.treichels.hott:hott-util:_")
    testImplementation("junit:junit:_")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:_")
}

semver {
    releaseTagNameFormat = "v%s"
}

version = semver.version

tasks {
    compileJava {
        options.compilerArgumentProviders.add(CommandLineArgumentProvider {
            // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
            listOf("--patch-module", "de.treichels.hott.model=${sourceSets["main"].output.asPath}")
        })
    }

    jar {
        manifest {
            attributes (
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            )
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MDL-Viewer/hott-model")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("github") {
            artifactId = project.name.lowercase()
            group = "de.treichels.hott"
            from(components["java"])
        }          
    }
}
