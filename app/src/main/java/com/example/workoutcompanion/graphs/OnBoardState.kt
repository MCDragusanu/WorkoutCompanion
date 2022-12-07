package com.example.workoutcompanion.graphs

import android.util.Log
import com.example.workoutcompanion.room.AccountInformation

data class OnBoardState(val accountInformation: AccountInformation? = null ,
                        val accountIsCreated:Boolean = false,
                        ) {
    fun logValues() {
        Log.d(
            "Test",
            accountInformation?.firstName +
                    "\n" + accountInformation?.lastName +
                    "\n" + accountInformation?.email +
                    "\n" + accountInformation?.dateOfBirth +
                    "\n" + accountInformation?.accountCreationDate +
                    "\n" + accountInformation?.gender+
                    "\n" + accountInformation?.lastEntry+
                    "\n" + accountInformation?.provider+
                    "\n" + accountInformation?.account_uid+
                    "\n" + accountInformation?.weightInKgs.toString()+
                    "\n" + accountInformation?.heightInCms.toString()+
                    "\n" + accountInformation?.gender.toString()+
                    "\n" + accountInformation?.rememberMe.toString()
        )
    }
}
