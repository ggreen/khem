package nyla.solutions.cheminformatics.khem.khemweb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KhemWebApplication

fun main(args: Array<String>) {
    SpringApplication.run(KhemWebApplication::class.java, *args)
}
