package com.android.binapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.binapp.data.storage.BinDAO
import com.android.binapp.domain.models.BinInfoEntity

@Database(
    version = 1, entities = [
        BinInfoEntity::class,
    ]
)
abstract class DataBase:RoomDatabase() {
    abstract fun getBinDAO(): BinDAO




}