package com.example.workoutcompanion.room


interface AccountRepository {

    suspend fun createAccount(
        newAccountInformation: AccountInformation,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    )


    suspend fun deleteAccount(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    )


    suspend fun updateAccount(
        newAccountInformation: AccountInformation,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    )


    suspend fun getCurrentAccount(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit = {},
        onSuccess: suspend (AccountInformation) -> Unit
    )


    suspend fun countAccounts(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit = {},
        onSuccess: suspend (Int) -> Unit = {}
    )

    sealed class AccountRepositoryException( val text: String) : Exception(text) {

        object RoomNoItemFound :
            AccountRepositoryException(text = "No item that matched the request")

        object RoomItemsOverlappingException :
            AccountRepositoryException(text = "There are at least 2 items instead of a single one")
    }
}