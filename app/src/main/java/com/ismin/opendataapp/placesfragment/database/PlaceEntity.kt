package com.ismin.opendataapp.placesfragment.database

import androidx.room.*
import com.ismin.opendataapp.R
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
    val website: String = "https://www.jcchevalier.fr/",
    val image: Int = R.drawable.stade_velodrome
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