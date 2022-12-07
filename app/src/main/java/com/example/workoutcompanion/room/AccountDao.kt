package com.example.workoutcompanion.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {
    @Insert
    suspend fun createAccount(newAccountInformation: AccountInformation)

    @Query("Delete  from user_account_table where account_uid = :accountUid")
    suspend fun deleteAccount(accountUid:String)

    @Update
    suspend fun updateAccount(newAccountInformation: AccountInformation)

    @Query("Select * from user_account_table where account_uid = :accountUid")
    suspend fun getCurrentAccount(accountUid: String):AccountInformation

    @Query("Select count(account_uid) from user_account_table where account_uid = :accountUid")
    suspend fun countAccounts(accountUid: String):Int


}