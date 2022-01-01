package me.jiayu.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun initDatabase(config: ApplicationConfig) {
    val driverClassName = config.property("storage.driverClassName").getString()
    val filePath = config.propertyOrNull("storage.dbFilePath")?.getString()?.let {
        File(it).canonicalFile.absolutePath
    } ?: ""
    val jdbcURL = config.property("storage.jdbcURL").getString() + filePath
    Database.connect(createHikariDatasource(url=jdbcURL,driver=driverClassName))

    transaction {
        SchemaUtils.create(Messages)
    }
}

private fun createHikariDatasource(url: String, driver: String)= HikariDataSource(HikariConfig().apply {
    jdbcUrl =url
    driverClassName=driver
    maximumPoolSize =3
    isAutoCommit=false
    transactionIsolation="TRANSACTION_REPEATABLE_READ"
    validate()
})



object Messages : Table() {
    val id = integer("id").autoIncrement()
    val text = varchar("body", 1024)
    override val primaryKey = PrimaryKey(id)
}