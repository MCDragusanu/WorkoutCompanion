package com.example.workoutcompanion.graphs

import androidx.lifecycle.ViewModel
import com.example.workoutcompanion.common.Height
import com.example.workoutcompanion.common.Weight

class EntryGraphViewModel: ViewModel(){
       private var _onBoardState = OnBoardState()
        var onBoardState = _onBoardState

        private set
        fun setState(newState: OnBoardState){
            this._onBoardState = newState.copy(accountIsCreated = true)
        }
        fun setWeight(weight: Weight, unifOfMeasurement:Int){
            _onBoardState = _onBoardState.copy(
                accountInformation = onBoardState.accountInformation?.copy(
                    weightInKgs = weight.toKgs(),
                    unitOfMeasurementWeight = unifOfMeasurement
                )
            )

        }
        fun setHeight(height: Height, unifOfMeasurement: Int){
            _onBoardState = _onBoardState.copy(
                accountInformation = onBoardState.accountInformation?.copy(
                    heightInCms = height.toCms(),
                    unitOfMeasureHeight = unifOfMeasurement
                )
            )
        }

    }