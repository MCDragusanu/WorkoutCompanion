package com.example.workoutcompanion.room

class AccountRepositoryImpl(private val dao: AccountDao):AccountRepository {

    override suspend fun createAccount(
        newAccountInformation: AccountInformation,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit,
        onSuccess: suspend () -> Unit
    ) {
        try {
            onStart()
            dao.createAccount(newAccountInformation)
            onSuccess()

        }catch (e:Exception){
            onError(e)
        }
    }

    override suspend fun deleteAccount(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit,
        onSuccess: suspend () -> Unit
    ) {
        try {
            val count = dao.countAccounts(accountUid)
            onStart()
            if(count == 0) throw AccountRepository.AccountRepositoryException.RoomNoItemFound
            if(count >1) throw AccountRepository.AccountRepositoryException.RoomItemsOverlappingException
            else {
                dao.deleteAccount(accountUid)
                onSuccess()
            }
        }catch (e:Exception){
            onError(e)
        }
    }
    override suspend fun updateAccount(
        newAccountInformation: AccountInformation,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit,
        onSuccess: suspend () -> Unit
    ) {
        try {
            val count = dao.countAccounts(newAccountInformation.account_uid)
            onStart()
            if(count == 0) throw AccountRepository.AccountRepositoryException.RoomNoItemFound
            if(count >1) throw AccountRepository.AccountRepositoryException.RoomItemsOverlappingException
            else {
                dao.updateAccount(newAccountInformation)
                onSuccess()
            }
        }catch (e:Exception){
            onError(e)
        }
    }
    override suspend fun getCurrentAccount(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit,
        onSuccess: suspend (AccountInformation) -> Unit
    ) {
        try {
            val count = dao.countAccounts(accountUid)
            onStart()
            if(count == 0) throw AccountRepository.AccountRepositoryException.RoomNoItemFound
            if(count >1) throw AccountRepository.AccountRepositoryException.RoomItemsOverlappingException
            else {
                val account = dao.getCurrentAccount(accountUid)
                onSuccess(account)
            }
        }catch (e:Exception){
            onError(e)
        }
    }
    override suspend fun countAccounts(
        accountUid: String,
        onError: suspend (Exception) -> Unit,
        onStart: suspend () -> Unit,
        onSuccess: suspend (Int) -> Unit
    ) {
        try {
            onStart()
            val count = dao.countAccounts(accountUid)
            onSuccess(count)
        }catch (e:Exception){
            onError(e)
        }
    }
}