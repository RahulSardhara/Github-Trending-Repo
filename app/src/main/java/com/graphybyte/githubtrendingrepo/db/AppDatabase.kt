package com.graphybyte.githubtrendingrepo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.graphybyte.githubtrendingrepo.db.converter.TimestampConverter
import com.graphybyte.githubtrendingrepo.db.dao.GithubDao
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity

@Database(entities = [GithubEntity::class], version = 1)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Declare all the DAOs here..
     */
    abstract fun githubDao(): GithubDao

    /**
     * create room database instance
     */
    companion object {

        @Volatile
        private var DB_INSTANCE: AppDatabase? = null
        private const val DB_NAME = "app_db"

        fun getDatabase(context: Context): AppDatabase {
            return DB_INSTANCE ?: synchronized(this) {
                val roomInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                DB_INSTANCE = roomInstance
                roomInstance
            }
        }

    }
}