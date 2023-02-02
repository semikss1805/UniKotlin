package com.example.lab_3

import androidx.room.*

@Entity
data class StringItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var item: String
)

@Dao
interface StringItemDao {
    @Insert
    suspend fun insert(item: StringItem)

    @Query("SELECT * FROM stringItem")
    suspend fun getAll(): List<StringItem>
}

@Database(entities = [StringItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stringItemDao(): StringItemDao
}