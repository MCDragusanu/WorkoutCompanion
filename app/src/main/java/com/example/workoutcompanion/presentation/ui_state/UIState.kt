package com.example.workoutcompanion.presentation.ui_state

/*Mai bine sa fac fara payload ca sa pot sa decid daca trimit sau nu argumente*/
enum class UIState{
     Default,
     Loading,
     Pressed,
     Completed,
     Selected,
     Error,
     Disabled;

     fun isError() = this == Error
}

