package com.damianf.simpleweatherapp.data.db

import androidx.room.*
import java.sql.Date

@Dao
interface LocationDao {
    @Query("SELECT * from location_table")
    fun getLastLocation(): Location

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLocation(location: Location)
}

@Entity(tableName = "location_table")
data class Location(@ColumnInfo(name = "city_name") val cityName: String) {
    @PrimaryKey(autoGenerate = false)
    var id = 0
}