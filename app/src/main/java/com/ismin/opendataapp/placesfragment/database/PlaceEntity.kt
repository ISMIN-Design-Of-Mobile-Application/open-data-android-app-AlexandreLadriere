package com.ismin.opendataapp.placesfragment.database

import androidx.room.*
import java.io.Serializable

@Entity
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String,
    val distance: String,
    val latitude: String,
    val longitude: String,
    val website: String,
    val image: Int
) : Serializable

@Dao
interface PlaceDAO {

    @Query("SELECT * FROM PlaceEntity")
    fun getAll(): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg placeEntity: PlaceEntity)

    @Delete
    fun delete(placeEntity: PlaceEntity)

    @Query("DELETE FROM PlaceEntity")
    fun deleteAll()
}