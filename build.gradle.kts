plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

configure<org.springframework.boot.gradle.dsl.SpringBootExtension> {
    mainClass.set("app.helipay.core.Application")
}

dependencies {

}


//tasks.named<Test>("test") {
//    useJUnitPlatform()
//}

