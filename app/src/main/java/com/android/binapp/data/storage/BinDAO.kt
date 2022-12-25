package com.android.binapp.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.binapp.domain.models.BinInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BinDAO {

    @Query("SELECT * FROM bin_info")
    fun getAll(): Flow<List<BinInfoEntity>>

    @Insert
    fun insertInfo(binInfoEntity: BinInfoEntity )

    @Query("DELETE FROM bin_info")
    fun deleteAll()

    @Query("DELETE FROM bin_info WHERE id = :id_")
    fun delete(id_:Int)



}