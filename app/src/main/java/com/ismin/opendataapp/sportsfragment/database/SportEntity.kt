package com.ismin.opendataapp.sportsfragment.database

import androidx.room.*
import java.io.Serializable

@Entity
data class SportEntity(
    @PrimaryKey
    val id: Int,
    val name: String
) : Serializable {
    @Ignore
    var isEnabled: Boolean = false
}

@Dao
interface SportDAO {

    @Query("SELECT * FROM SportEntity")
    fun getAll(): List<SportEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg sportEntity: SportEntity)

    @Delete
    fun delete(sportEntity: SportEntity)
}