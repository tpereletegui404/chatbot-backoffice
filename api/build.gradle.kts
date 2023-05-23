import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.nbottarini.asimov.environment.Env
import nu.studer.gradle.jooq.JooqGenerate
import org.flywaydb.gradle.task.AbstractFlywayTask
import org.flywaydb.gradle.task.FlywayCleanTask
import org.flywaydb.gradle.task.FlywayInfoTask
import org.flywaydb.gradle.task.FlywayMigrateTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.MatcherRule
import org.jooq.meta.jaxb.MatcherTransformType
import org.jooq.meta.jaxb.Matchers
import org.jooq.meta.jaxb.MatchersTableType

Env.addSearchPath(rootProject.projectDir.absolutePath)

plugins {
    kotlin("jvm") version "1.7.10"
    id("nu.studer.jooq") version "7.1.1"
    id("org.flywaydb.flyway") version "8.5.13"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

buildscript {
    dependencies { classpath("com.nbottarini:asimov-environment:2.0.0") }
}

group = "com.proyecto404.backoffice"
version = rootProject.file("VERSION").readText().trim()

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.flywaydb:flyway-core:9.8.3")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("com.nbottarini:asimov-kotlin-extensions:1.0.2")
    implementation("com.nbottarini:asimov-environment:2.0.0")
    implementation("com.nbottarini:asimov-time:0.5.1")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.javalin:javalin:4.6.4")
    implementation("com.nbottarini:asimov-cqbus:0.5.4")
    implementation("com.nbottarini:asimov-json:0.5.2")
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.jooq:jooq:7.1.1")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.apache.commons:commons-pool2:2.11.1")
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jsoup:jsoup:1.15.4")
    jooqGenerator("org.postgresql:postgresql:42.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("net.bytebuddy:byte-buddy:1.12.18") // Added for mockk compatibility with JDK16
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("io.rest-assured:rest-assured:5.2.0")

}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories { mavenCentral() }

    tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "16" }

    tasks.withType<Test> {
        useJUnitPlatform { excludeTags("slow") }
    }

    tasks.register<Test>("slowTest") {
        group = "verification"
        useJUnitPlatform { includeTags("slow") }
    }

    tasks.register<Test>("allTests") {
        group = "verification"
        useJUnitPlatform { }
    }

    tasks.withType<Test>().configureEach {
        reports.html.required.set(false)
        reports.junitXml.required.set(false)
    }

    kotlin {
        sourceSets["main"].apply {
            kotlin.srcDirs("src", "generated")
            resources.srcDirs("resources")
        }
        sourceSets["test"].apply {
            kotlin.srcDir("test")
            resources.srcDir("test_resources")
        }
    }

    java {
        sourceSets["main"].apply {
            java.srcDirs("src", "generated")
            resources.srcDirs("resources")
        }
        sourceSets["test"].apply {
            java.srcDir("test")
            resources.srcDir("test_resources")
        }
    }
}

tasks.getByName<ShadowJar>("shadowJar") {
    archiveFileName.set("chatbot-backoffice_api.jar")
    manifest {
        attributes(mapOf(
            "Main-Class" to "com.proyecto404.backoffice.MainKt",
            "VERSION" to project.version
        ))
    }
}

flyway {
    url = "jdbc:postgresql://${Env["DB_HOST"]}:${Env["DB_PORT"]}/${Env["DB_NAME"]}"
    user = Env["DB_USER"]
    password = Env["DB_PASSWORD"]
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:${project.projectDir}/resources/db")
}

fun setupFlywayTestDB(task: AbstractFlywayTask) {
    task.apply {
        url = "jdbc:postgresql://${Env["TEST_DB_HOST"]}:${Env["TEST_DB_PORT"]}/${Env["TEST_DB_NAME"]}"
        user = Env["TEST_DB_USER"]
        password = Env["TEST_DB_PASSWORD"]
        schemas = arrayOf("public")
        locations = arrayOf("filesystem:${project.projectDir}/resources/db/structure", "filesystem:${project.projectDir}/resources/db/master-data")
    }
}

tasks.register<FlywayMigrateTask>("flywayMigrateTest") { setupFlywayTestDB(this) }
tasks.register<FlywayCleanTask>("flywayCleanTest") { setupFlywayTestDB(this) }
tasks.register<FlywayInfoTask>("flywayInfoTest") { setupFlywayTestDB(this) }

jooq {
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(Env["GENERATE_JOOQ"] == "1")
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://${Env["DB_HOST"]}:${Env["DB_PORT"]}/${Env["DB_NAME"]}"
                    user = Env["DB_USER"]
                    password = Env["DB_PASSWORD"]
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    strategy.apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                        matchers = Matchers().apply {
                            tables = listOf(
                                MatchersTableType().apply {
                                    expression = ""
                                    pojoClass = MatcherRule().apply {
                                        expression = "\$0_dto"
                                        transform = MatcherTransformType.PASCAL
                                    }
                                }
                            )
                        }
                    }
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        includes = ".*"
                        excludes = ""
                        isIncludeSystemSequences = true
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isDaos = true
                        isFluentSetters = true
                        newline = "\\r\\n"
                    }
                    target.apply {
                        packageName = "com.proyecto404.backoffice.infrastructure.jooq.generated"
                        directory = "generated"
                    }
                }
            }
        }
    }
}

tasks.named<JooqGenerate>("generateJooq") {
    dependsOn("flywayMigrate")
    dependsOn("flywayMigrateTest")
    inputs.files(fileTree("resources/db"))
        .withPropertyName("migrations")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    allInputsDeclared.set(true) // make jOOQ task participate in incremental builds and build caching
    outputs.cacheIf { true }
}
