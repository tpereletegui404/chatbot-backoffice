import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow")
    id("nu.studer.jooq")
}

dependencies {
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation("com.nbottarini:asimov-environment:2.0.0")
    implementation(rootProject)
    implementation(rootProject.sourceSets["test"].output)
}

tasks.getByName<ShadowJar>("shadowJar") {
    archiveFileName.set("populateDb.jar")
    manifest {
        attributes(mapOf(
            "Main-Class" to "com.proyecto404.finanz.populateDb.PopulateDbMainKt"
        ))
    }
}
