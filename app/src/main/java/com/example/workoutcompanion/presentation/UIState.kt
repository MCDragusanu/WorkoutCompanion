package com.example.workoutcompanion.presentation


/*Mai bine sa fac fara payload ca sa pot sa decid daca trimit sau nu argumente*/
sealed class UIState(val eventId:Int){
    object Default:UIState(0 )
    object Loading:UIState(1 )
    object Pressed:UIState(2)
    object Completed:UIState(3)
    object Selected:UIState(4)
    object Error:UIState(5)
    object Disabled:UIState(6)
}
