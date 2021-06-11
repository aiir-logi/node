plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.3.4"
    id("com.google.cloud.tools.jib") version "2.6.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"
    id("org.ajoberstar.grgit") version "4.1.0"
    jacoco
    id("org.sonarqube") version "3.0"
}

val baseVersion = mapOf (
    "major" to 0,
    "minor" to 0
)

fun getCalculatedVersion(): String {
    var versionFromTag = "0.0.0"
    val tags : List<org.ajoberstar.grgit.Tag> = grgit.tag.list()
    if(tags.isNotEmpty()) {
        val sortedTags : List<String> = tags.map { tag -> tag.name }
        sortedTags.sortedWith(compareBy({it.split(".")[0].toInt()}, {it.split(".")[1].toInt()}, { it.split(".")[2].toInt() }))
        versionFromTag = sortedTags[sortedTags.lastIndex]
    }
    var major : Int = versionFromTag.split(".")[0].toInt()
    var minor : Int = versionFromTag.split(".")[1].toInt()
    var patch : Int = versionFromTag.split(".")[2].toInt()
    when {
        baseVersion["major"]!! > versionFromTag.split(".")[0].toInt() -> {
            major = baseVersion["major"]!!
            minor = baseVersion["minor"]!!
            patch = 0
        }
        baseVersion["minor"]!! > versionFromTag.split(".")[1].toInt() -> {
            minor = baseVersion["minor"]!!
            patch = 0
        }
        else -> {
            patch += 1
        }
    }
    return "${major}.${minor}.${patch}"
}


version = System.getenv("ARTIFACTS_VERSION") ?: getCalculatedVersion()
group = "pwr.aiir"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("pwr.aiir.*")
    }
}

noArg {
    annotation("pwr.aiir.annotation.NoArg")
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    //implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kafka:micronaut-kafka")
    //runtimeOnly("io.micronaut:micronaut-discovery-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    //runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.testcontainers:postgresql")
    testImplementation("io.rest-assured:rest-assured:4.3.1")
    testImplementation("org.testcontainers:mongodb:1.15.2")
    implementation("ch.rasc:bsoncodec:1.0.1")
}


application {
    mainClass.set("pwr.aiir.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        reports.xml.isEnabled = true
    }


    jib {
        from {
            image = "gcr.io/distroless/java:11"
        }
        to {
            image = "aiirlogi/${project.name}:${project.version}"
        }
    }

}

sonarqube {
    properties {
        property("sonar.branch.name", (if (System.getProperty("sonar.pullrequest.key") == "") grgit.branch.current().name else ""))
    }
}
