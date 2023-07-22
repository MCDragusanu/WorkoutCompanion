package com.example.workoutcompanion.core.domain.model

public val exerciseList = listOf(
    Exercise(
        uid = 0,
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        exerciseName = "Barbell Flat Bench Press",
        equipmentList = listOf("Barbell", "Bench"),
        isBodyWeight = false,
        position = 0,
        gripStyle = GripStyle("Medium", "Neutral") ,
        overloadCoeff = 10.0f,
        techniqueCoeff = 6.50f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 10.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                1,
                0,
                1,
                "Setup",
                "Adjust the bench to a flat position and lie down on it."
            ) ,
            ExerciseInstruction(
                2,
                0,
                2,
                "Eccentric",
                "Grab the barbell with a medium grip width and unrack it."
            ) ,
            ExerciseInstruction(
                3,
                0,
                3,
                "Form",
                "Keep your elbows tucked close to your sides and your back slightly arched."
            ) ,
            ExerciseInstruction(
                4,
                0,
                4,
                "Concentric",
                "Lower the barbell to your chest while maintaining control."
            ) ,
            ExerciseInstruction(
                5,
                0,
                5,
                "Form",
                "Push the barbell back up to the starting position, focusing on squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                6,
                0,
                6,
                "Cooldown",
                "Rack the barbell once you've completed the desired number of repetitions."
            )
        ), movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.PowerliftingFocus.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) ,


    Exercise(
        uid = 1,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        exerciseName = "Barbell Incline Press",
        equipmentList = listOf("Barbell", "Bench"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Medium", "Neutral") ,
        overloadCoeff = 10f,
        techniqueCoeff = 7.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                7,
                1,
                1,
                "Setup",
                "Adjust the bench to an inclined position and lie down on it."
            ) ,
            ExerciseInstruction(
                8,
                1,
                2,
                "Eccentric",
                "Grab the barbell with a medium grip width and unrack it."
            ) ,
            ExerciseInstruction(
                9,
                1,
                3,
                "Form",
                "Keep your elbows tucked close to your sides and your back slightly arched."
            ) ,
            ExerciseInstruction(
                10,
                1,
                4,
                "Concentric",
                "Lower the barbell to your upper chest while maintaining control."
            ) ,
            ExerciseInstruction(
                11,
                1,
                5,
                "Form",
                "Push the barbell back up to the starting position, focusing on squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                12,
                1,
                6,
                "Cooldown",
                "Rack the barbell once you've completed the desired number of repetitions."
            )
        ), movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        exerciseName = "Barbell Decline Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        uid = 2,
        equipmentList = listOf("Barbell", "Bench"),
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Medium", "Neutral") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                13,
                2,
                1,
                "Setup",
                "Adjust the bench to a declined position and lie down on it."
            ) ,
            ExerciseInstruction(
                14,
                2,
                2,
                "Eccentric",
                "Grab the barbell with a medium grip width and unrack it."
            ) ,
            ExerciseInstruction(
                15,
                2,
                3,
                "Form",
                "Keep your elbows tucked close to your sides and your back slightly arched."
            ) ,
            ExerciseInstruction(
                16,
                2,
                4,
                "Concentric",
                "Lower the barbell to your lower chest while maintaining control."
            ) ,
            ExerciseInstruction(
                17,
                2,
                5,
                "Form",
                "Push the barbell back up to the starting position, focusing on squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                18,
                2,
                6,
                "Cooldown",
                "Rack the barbell once you've completed the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        uid = 3,
        exerciseName = "Dumbbell Flat Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dumbbells", "Bench"),
        isBodyWeight = false,
        position = 3,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                19,
                3,
                1,
                "Setup",
                "Adjust the bench to a flat position and sit on it with a dumbbell in each hand."
            ) ,
            ExerciseInstruction(
                20,
                3,
                2,
                "Eccentric",
                "Lower the dumbbells towards your chest, keeping your elbows slightly bent."
            ) ,
            ExerciseInstruction(
                21,
                3,
                3,
                "Form",
                "Maintain a slight arch in your back and engage your core muscles."
            ) ,
            ExerciseInstruction(
                22,
                3,
                4,
                "Concentric",
                "Press the dumbbells up above your chest, fully extending your arms."
            ) ,
            ExerciseInstruction(
                23,
                3,
                5,
                "Form",
                "Lower the dumbbells back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                24,
                3,
                6,
                "Cooldown",
                "Place the dumbbells on the floor once you have completed the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        uid = 4,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        exerciseName = "Dumbbell Incline Press",
        equipmentList = listOf("Dumbbells", "Bench"),
        isBodyWeight = false,
        position = 4,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                25,
                4,
                1,
                "Setup",
                "Adjust the bench to an inclined position and sit on it with a dumbbell in each hand."
            ) ,
            ExerciseInstruction(
                26,
                4,
                2,
                "Eccentric",
                "Lower the dumbbells towards your upper chest, maintaining a slight bend in your elbows."
            ) ,
            ExerciseInstruction(
                27,
                4,
                3,
                "Form",
                "Keep your back slightly arched and engage your core muscles."
            ) ,
            ExerciseInstruction(
                28,
                4,
                4,
                "Concentric",
                "Press the dumbbells up above your chest, fully extending your arms."
            ) ,
            ExerciseInstruction(
                29,
                4,
                5,
                "Form",
                "Lower the dumbbells back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                30,
                4,
                6,
                "Cooldown",
                "Place the dumbbells on the floor once you have completed the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        uid = 5,
        exerciseName = "Dumbbell Decline Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dumbbells", "Bench"),
        isBodyWeight = false,
        position = 5,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                31,
                5,
                1,
                "Setup",
                "Adjust the bench to a declined position and sit on it with a dumbbell in each hand."
            ) ,
            ExerciseInstruction(
                32,
                5,
                2,
                "Eccentric",
                "Lower the dumbbells towards your lower chest, keeping your elbows slightly bent."
            ) ,
            ExerciseInstruction(
                33,
                5,
                3,
                "Form",
                "Maintain a slight arch in your back and engage your core muscles."
            ) ,
            ExerciseInstruction(
                34,
                5,
                4,
                "Concentric",
                "Press the dumbbells up above your chest, fully extending your arms."
            ) ,
            ExerciseInstruction(
                35,
                5,
                5,
                "Form",
                "Lower the dumbbells back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                36,
                5,
                6,
                "Cooldown",
                "Place the dumbbells on the floor once you have completed the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        uid = 6,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        exerciseName = "Bodyweight Dips",
        equipmentList = listOf("Dip Bars"),
        isBodyWeight = true,
        position = 2,
        gripStyle = GripStyle("Neutral", "Wide") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 6.5f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                43,
                6,
                1,
                "Setup",
                "Position yourself between the dip bars and hold onto them with a neutral grip."
            ) ,
            ExerciseInstruction(
                44,
                6,
                2,
                "Eccentric",
                "Lower your body by bending your elbows and leaning forward slightly."
            ) ,
            ExerciseInstruction(
                45,
                6,
                3,
                "Form",
                "Maintain an upright chest, keep your shoulders down, and engage your core throughout the movement."
            ) ,
            ExerciseInstruction(
                46,
                6,
                4,
                "Concentric",
                "Push yourself back up by extending your elbows and contracting your chest muscles."
            ) ,
            ExerciseInstruction(
                47,
                6,
                5,
                "Form",
                "Focus on controlled and smooth movements throughout the exercise."
            ) ,
            ExerciseInstruction(
                48,
                6,
                6,
                "Cooldown",
                "Finish the set and carefully step off the dip bars."
            )
        ),
        movement = Movement.ChestDips ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.CalisthenicsFocus.uid
        )
    ) , Exercise(
        uid = 7,
        exerciseName = "Weighted Dips",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dip Bars", "Weight Belt"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Neutral", "Wide") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                37,
                7,
                1,
                "Setup",
                "Position yourself between the dip bars and secure a weight belt around your waist."
            ) ,
            ExerciseInstruction(
                38,
                7,
                2,
                "Eccentric",
                "Lower your body by bending your elbows and leaning forward slightly."
            ) ,
            ExerciseInstruction(
                39,
                7,
                3,
                "Form",
                "Maintain an upright chest, keep your shoulders down, and engage your core throughout the movement."
            ) ,
            ExerciseInstruction(
                40,
                7,
                4,
                "Concentric",
                "Push yourself back up by extending your elbows and contracting your chest muscles."
            ) ,
            ExerciseInstruction(
                41,
                7,
                5,
                "Form",
                "Focus on controlled and smooth movements throughout the exercise."
            ) ,
            ExerciseInstruction(
                42,
                7,
                6,
                "Cooldown",
                "Finish the set and carefully step off the dip bars."
            )
        ),
        movement = Movement.ChestDips ,
        filters = listOf(
            ExerciseFilter.WristPain.uid,
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.CalisthenicsFocus.uid
        )
    ) , Exercise(
        uid = 8,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        exerciseName = "High-Low Fly",
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Neutral", "Wide") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                49,
                8,
                1,
                "Setup",
                "Adjust the cable machine to a high position and attach the handles."
            ) ,
            ExerciseInstruction(
                50,
                8,
                2,
                "Form",
                "Stand in a split stance and hold one handle in each hand."
            ) ,
            ExerciseInstruction(
                51,
                8,
                3,
                "Eccentric",
                "With a slight bend in your elbows, bring your arms down and slightly forward, maintaining a controlled motion."
            ) ,
            ExerciseInstruction(
                52,
                8,
                4,
                "Concentric",
                "Bring your arms back up and slightly outward, squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                53,
                8,
                5,
                "Form",
                "Maintain proper posture and keep your core engaged throughout the exercise."
            ) ,
            ExerciseInstruction(54, 8, 6, "Cooldown", "Finish the set and release the handles.")
        ),
        movement = Movement.ChestFly ,
        filters = listOf(ExerciseFilter.ShoulderPain.uid, ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 9,
        exerciseName = "Low-High Fly",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Neutral", "Wide") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                55,
                9,
                1,
                "Setup",
                "Adjust the cable machine to a low position and attach the handles."
            ) ,
            ExerciseInstruction(
                56,
                9,
                2,
                "Form",
                "Stand in a split stance and hold one handle in each hand."
            ) ,
            ExerciseInstruction(
                57,
                9,
                3,
                "Eccentric",
                "With a slight bend in your elbows, bring your arms up and slightly backward, maintaining a controlled motion."
            ) ,
            ExerciseInstruction(
                58,
                9,
                4,
                "Concentric",
                "Bring your arms down and slightly inward, squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                59,
                9,
                5,
                "Form",
                "Maintain proper posture and keep your core engaged throughout the exercise."
            ) ,
            ExerciseInstruction(60, 9, 6, "Cooldown", "Finish the set and release the handles.")
        ),
        movement = Movement.ChestFly ,
        filters = listOf(ExerciseFilter.ShoulderPain.uid, ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 10,
        exerciseName = "Peck Deck Fly",
        equipmentList = listOf("Peck Deck Machine"),
        isBodyWeight = false,
        position = 3,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        gripStyle = GripStyle("Neutral", "Fixed") ,
        overloadCoeff = 9.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 6.5f,
        rangeOfMotion = 9.5f,
        instructionList = listOf(
            ExerciseInstruction(
                61,
                10,
                1,
                "Setup",
                "Adjust the seat and position yourself in the peck deck machine."
            ) ,
            ExerciseInstruction(
                62,
                10,
                2,
                "Form",
                "Place your forearms against the padded levers and hold onto the handles."
            ) ,
            ExerciseInstruction(
                63,
                10,
                3,
                "Eccentric",
                "Squeeze your chest muscles as you bring the levers together, maintaining a controlled motion."
            ) ,
            ExerciseInstruction(
                64,
                10,
                4,
                "Concentric",
                "Slowly release the levers back to the starting position, maintaining tension in your chest muscles."
            ) ,
            ExerciseInstruction(
                65,
                10,
                5,
                "Form",
                "Maintain proper posture and keep your core engaged throughout the exercise."
            ) ,
            ExerciseInstruction(
                66,
                10,
                6,
                "Cooldown",
                "Finish the set and release the handles."
            )
        ),
        movement = Movement.ChestFly ,
        filters = listOf(ExerciseFilter.ShoulderPain.uid, ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        exerciseName = "Dumbbell Fly",
        uid = 11,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 4,
        gripStyle = GripStyle("Neutral", "Wide") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 6.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                67,
                11,
                1,
                "Setup",
                "Lie on a flat bench with a dumbbell in each hand, arms extended upward and palms facing each other."
            ) ,
            ExerciseInstruction(
                68,
                11,
                2,
                "Form",
                "Maintain a slight bend in your elbows throughout the exercise."
            ) ,
            ExerciseInstruction(
                69,
                11,
                3,
                "Eccentric",
                "Lower the dumbbells in a wide arc, feeling a stretch in your chest muscles."
            ) ,
            ExerciseInstruction(
                70,
                11,
                4,
                "Concentric",
                "Bring the dumbbells back up to the starting position, squeezing your chest muscles."
            ) ,
            ExerciseInstruction(
                71,
                11,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                72,
                11,
                6,
                "Cooldown",
                "Finish the set and carefully place the dumbbells back on the floor."
            )
        ),
        movement = Movement.ChestFly ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.Isolation.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        exerciseName = "Arnold Dumbbell Seated Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        uid = 12,
        equipmentList = listOf("Dumbbells", "Bench"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                73,
                12,
                1,
                "Setup",
                "Sit on a bench with a dumbbell in each hand, palms facing forward."
            ) ,
            ExerciseInstruction(
                74,
                12,
                2,
                "Form",
                "Raise the dumbbells to shoulder level, keeping your elbows bent."
            ) ,
            ExerciseInstruction(
                75,
                12,
                3,
                "Concentric",
                "Press the dumbbells overhead, fully extending your arms."
            ) ,
            ExerciseInstruction(
                76,
                12,
                4,
                "Eccentric",
                "Lower the dumbbells back to shoulder level with control."
            ) ,
            ExerciseInstruction(
                77,
                12,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                78,
                12,
                6,
                "Cooldown",
                "Finish the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        exerciseName = "Military Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        uid = 13,
        equipmentList = listOf("Barbell"),
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Wide", "Pronated") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 9.5f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                79,
                13,
                1,
                "Setup",
                "Stand upright with a barbell in front of your shoulders."
            ) ,
            ExerciseInstruction(
                80,
                13,
                2,
                "Form",
                "Grasp the barbell with a wide grip, palms facing forward."
            ) ,
            ExerciseInstruction(
                81,
                13,
                3,
                "Concentric",
                "Press the barbell overhead, fully extending your arms."
            ) ,
            ExerciseInstruction(
                82,
                13,
                4,
                "Eccentric",
                "Lower the barbell back to the starting position with control."
            ) ,
            ExerciseInstruction(
                83,
                13,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                84,
                13,
                6,
                "Cooldown",
                "Finish the set and carefully place the barbell back on the floor."
            )
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.LowerBackPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid
        )
    ) , Exercise(
        exerciseName = "Seated Barbell Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        uid = 14,
        equipmentList = listOf("Barbell", "Bench"),
        isBodyWeight = false,
        position = 3,
        gripStyle = GripStyle("Medium", "Neutral") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                85,
                14,
                1,
                "Setup",
                "Sit on a bench with a barbell positioned at shoulder height."
            ) ,
            ExerciseInstruction(
                86,
                14,
                2,
                "Form",
                "Grasp the barbell with a medium grip, palms facing forward."
            ) ,
            ExerciseInstruction(
                87,
                14,
                3,
                "Concentric",
                "Press the barbell overhead, extending your arms fully."
            ) ,
            ExerciseInstruction(
                88,
                14,
                4,
                "Eccentric",
                "Lower the barbell back to the starting position with control."
            ) ,
            ExerciseInstruction(
                89,
                14,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(90, 14, 6, "Cooldown", "Finish the set and rack the barbell.")
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid
        )
    ) , Exercise(
        exerciseName = "Seated Dumbbell Press",
        uid = 15,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dumbbells", "Bench"),
        isBodyWeight = false,
        position = 4,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                91,
                15,
                1,
                "Setup",
                "Sit on a bench with a dumbbell in each hand, palms facing forward."
            ) ,
            ExerciseInstruction(
                92,
                15,
                2,
                "Form",
                "Raise the dumbbells to shoulder level, keeping your elbows bent."
            ) ,
            ExerciseInstruction(
                93,
                15,
                3,
                "Concentric",
                "Press the dumbbells overhead, fully extending your arms."
            ) ,
            ExerciseInstruction(
                94,
                15,
                4,
                "Eccentric",
                "Lower the dumbbells back to shoulder level with control."
            ) ,
            ExerciseInstruction(
                95,
                15,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                96,
                15,
                6,
                "Cooldown",
                "Finish the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        exerciseName = "Upright Arnold Press",
        uid = 16,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 5,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 8.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                97,
                16,
                1,
                "Setup",
                "Stand upright with a dumbbell in each hand, palms facing your body."
            ) ,
            ExerciseInstruction(
                98,
                16,
                2,
                "Form",
                "Start with your palms facing your body at shoulder level."
            ) ,
            ExerciseInstruction(
                99,
                16,
                3,
                "Concentric",
                "Rotate your palms forward and press the dumbbells overhead, extending your arms fully."
            ) ,
            ExerciseInstruction(
                100,
                16,
                4,
                "Eccentric",
                "Lower the dumbbells back to shoulder level with control, rotating your palms back to the starting position."
            ) ,
            ExerciseInstruction(
                101,
                16,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                102,
                16,
                6,
                "Cooldown",
                "Finish the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(
            ExerciseFilter.ShoulderPain.uid,
            ExerciseFilter.LowerBackPain.uid,
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid
        )
    ) , Exercise(
        exerciseName = "Machine Press",
        uid = 17,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Machine"),
        isBodyWeight = false,
        position = 6,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 6.0f,
        techniqueCoeff = 6.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                103,
                17,
                1,
                "Setup",
                "Adjust the machine seat and handles to a comfortable position."
            ) ,
            ExerciseInstruction(
                104,
                17,
                2,
                "Form",
                "Grasp the handles with a neutral grip, palms facing inward."
            ) ,
            ExerciseInstruction(
                105,
                17,
                3,
                "Concentric",
                "Press the handles forward and upward, extending your arms fully."
            ) ,
            ExerciseInstruction(
                106,
                17,
                4,
                "Eccentric",
                "Control the handles as you lower them back to the starting position."
            ) ,
            ExerciseInstruction(
                107,
                17,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                108,
                17,
                6,
                "Cooldown",
                "Finish the set and release the handles."
            )
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        exerciseName = "Smith Machine Press",
        uid = 18,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Smith Machine", "Bench"),
        isBodyWeight = false,
        position = 7,
        gripStyle = GripStyle("Medium", "Neutral") ,
        overloadCoeff = 6.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 7.0f,
        instructionList = listOf(
            ExerciseInstruction(
                109,
                18,
                1,
                "Setup",
                "Set the Smith machine barbell at shoulder height."
            ) ,
            ExerciseInstruction(
                110,
                18,
                2,
                "Form",
                "Stand with your feet shoulder-width apart and grasp the barbell with a medium grip, palms facing forward."
            ) ,
            ExerciseInstruction(
                111,
                18,
                3,
                "Concentric",
                "Press the barbell upward, extending your arms fully."
            ) ,
            ExerciseInstruction(
                112,
                18,
                4,
                "Eccentric",
                "Lower the barbell back to the starting position with control."
            ) ,
            ExerciseInstruction(
                113,
                18,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(114, 18, 6, "Cooldown", "Finish the set and rack the barbell.")
        ),
        movement = Movement.ShoulderPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        exerciseName = "Standing Lateral Fly",
        uid = 19,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                115,
                19,
                1,
                "Setup",
                "Stand with your feet shoulder-width apart and hold a dumbbell in each hand at your sides."
            ) ,
            ExerciseInstruction(
                116,
                19,
                2,
                "Form",
                "Raise your arms laterally to the sides until they are parallel to the floor."
            ) ,
            ExerciseInstruction(
                117,
                19,
                3,
                "Concentric",
                "Squeeze your shoulder blades together as you raise your arms."
            ) ,
            ExerciseInstruction(
                118,
                19,
                4,
                "Eccentric",
                "Lower your arms back to the starting position with control."
            ) ,
            ExerciseInstruction(
                119,
                19,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                120,
                19,
                6,
                "Cooldown",
                "Finish the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderLateralFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        exerciseName = "Seated Lateral Fly",
        uid = 20,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Dumbbells", "Seat"),
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 6.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 6.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                121,
                20,
                1,
                "Setup",
                "Sit on a bench with a dumbbell in each hand, palms facing your body."
            ) ,
            ExerciseInstruction(
                122,
                20,
                2,
                "Form",
                "Start with your arms hanging down at your sides."
            ) ,
            ExerciseInstruction(
                123,
                20,
                3,
                "Concentric",
                "Raise your arms laterally to the sides until they are parallel to the floor."
            ) ,
            ExerciseInstruction(
                124,
                20,
                4,
                "Eccentric",
                "Lower your arms back to the starting position with control."
            ) ,
            ExerciseInstruction(
                125,
                20,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                126,
                20,
                6,
                "Cooldown",
                "Finish the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderLateralFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 21,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        exerciseName = "Cable Lateral Fly",
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 3,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                127,
                21,
                1,
                "Setup",
                "Attach a handle to the cable machine at shoulder height."
            ) ,
            ExerciseInstruction(
                128,
                21,
                2,
                "Form",
                "Stand with your side facing the machine and hold the handle with one hand."
            ) ,
            ExerciseInstruction(
                129,
                21,
                3,
                "Concentric",
                "Raise your arm laterally, away from the machine, until it is parallel to the floor."
            ) ,
            ExerciseInstruction(
                130,
                21,
                4,
                "Eccentric",
                "Lower your arm back to the starting position with control."
            ) ,
            ExerciseInstruction(
                131,
                21,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                132,
                21,
                6,
                "Cooldown",
                "Complete the set and switch sides to work the other arm."
            )
        ),
        movement = Movement.ShoulderLateralFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 22,
        exerciseName = "Machine Lateral Fly",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Lateral Raise Machine"),
        isBodyWeight = false,
        position = 4,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 6.0f,
        techniqueCoeff = 6.5f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 6.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                133,
                22,
                1,
                "Setup",
                "Adjust the seat and handles of the lateral raise machine to a comfortable position."
            ) ,
            ExerciseInstruction(
                134,
                22,
                2,
                "Form",
                "Sit with your back against the pad and grasp the handles with a neutral grip."
            ) ,
            ExerciseInstruction(
                135,
                22,
                3,
                "Concentric",
                "Raise your arms laterally until they are parallel to the floor."
            ) ,
            ExerciseInstruction(
                136,
                22,
                4,
                "Eccentric",
                "Lower your arms back to the starting position with control."
            ) ,
            ExerciseInstruction(
                137,
                22,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                138,
                22,
                6,
                "Cooldown",
                "Complete the set and release the handles."
            )
        ),
        movement = Movement.ShoulderLateralFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 23,
        exerciseName = "Peck Deck Rear Fly",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Peck Deck Machine"),
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                139,
                23,
                1,
                "Setup",
                "Adjust the seat and handles of the peck deck machine to a comfortable position."
            ) ,
            ExerciseInstruction(
                140,
                23,
                2,
                "Form",
                "Sit with your back against the pad and place your forearms on the pads."
            ) ,
            ExerciseInstruction(
                141,
                23,
                3,
                "Concentric",
                "Squeeze your shoulder blades together as you bring the pads backward."
            ) ,
            ExerciseInstruction(
                142,
                23,
                4,
                "Eccentric",
                "Return the pads to the starting position with control."
            ) ,
            ExerciseInstruction(
                143,
                23,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                144,
                23,
                6,
                "Cooldown",
                "Complete the set and release the handles."
            )
        ),
        movement = Movement.ShoulderRearFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 24,
        exerciseName = "Dumbbell Rear Fly",
        equipmentList = listOf("Dumbbells"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 6.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 6.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                145,
                24,
                1,
                "Setup",
                "Stand with your feet shoulder-width apart and hold a dumbbell in each hand."
            ) ,
            ExerciseInstruction(
                146,
                24,
                2,
                "Form",
                "Bend your knees slightly and hinge forward at the hips, keeping your back flat."
            ) ,
            ExerciseInstruction(
                147,
                24,
                3,
                "Concentric",
                "Raise your arms laterally to the sides until they are parallel to the floor."
            ) ,
            ExerciseInstruction(
                148,
                24,
                4,
                "Eccentric",
                "Lower your arms back to the starting position with control."
            ) ,
            ExerciseInstruction(
                149,
                24,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                150,
                24,
                6,
                "Cooldown",
                "Complete the set and rest the dumbbells on your thighs."
            )
        ),
        movement = Movement.ShoulderRearFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 25,
        exerciseName = "Cable Rear Fly",
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        position = 3,
        gripStyle = GripStyle("Neutral", "Neutral") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                151,
                25,
                1,
                "Setup",
                "Attach a handle to the cable machine at hip height."
            ) ,
            ExerciseInstruction(
                152,
                25,
                2,
                "Form",
                "Stand with your back facing the machine and hold the handle with one hand."
            ) ,
            ExerciseInstruction(
                153,
                25,
                3,
                "Concentric",
                "Pull the handle back and away from the machine, squeezing your shoulder blade."
            ) ,
            ExerciseInstruction(
                154,
                25,
                4,
                "Eccentric",
                "Return the handle to the starting position with control."
            ) ,
            ExerciseInstruction(
                155,
                25,
                5,
                "Form",
                "Maintain proper posture and engage your core throughout the exercise."
            ) ,
            ExerciseInstruction(
                156,
                25,
                6,
                "Cooldown",
                "Complete the set and switch sides to work the other arm."
            )
        ),
        movement = Movement.ShoulderRearFly ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 26,
        exerciseName = "Barbell Row",
        equipmentList = listOf("Barbell"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        isBodyWeight = false,
        position = 1,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                157,
                26,
                1,
                "Setup",
                "Stand with your feet hip-width apart and slightly bend your knees. Bend forward at the hips while keeping your back straight."
            ) ,
            ExerciseInstruction(
                158,
                26,
                2,
                "Form",
                "Grasp the barbell with an overhand grip, hands slightly wider than shoulder-width apart."
            ) ,
            ExerciseInstruction(
                159,
                26,
                3,
                "Concentric",
                "Pull the barbell up toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                160,
                26,
                4,
                "Eccentric",
                "Lower the barbell back down to the starting position with control, maintaining a straight back."
            ) ,
            ExerciseInstruction(
                161,
                26,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to lift the weight."
            ) ,
            ExerciseInstruction(
                162,
                26,
                6,
                "Cooldown",
                "Complete the set and carefully place the barbell back on the ground."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.LowerBackPain.uid,
            ExerciseFilter.PowerliftingFocus.uid
        )
    ) , Exercise(
        uid = 27,
        exerciseName = "Wide Grip Cable Row",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 2,
        gripStyle = GripStyle("Overhand", "Wide") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                163,
                27,
                1,
                "Setup",
                "Attach a wide-grip handle to the cable machine at chest height."
            ) ,
            ExerciseInstruction(
                164,
                27,
                2,
                "Form",
                "Stand facing the cable machine and grab the handle with an overhand grip, hands wider than shoulder-width apart."
            ) ,
            ExerciseInstruction(
                165,
                27,
                3,
                "Concentric",
                "Pull the handle toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                166,
                27,
                4,
                "Eccentric",
                "Slowly release the handle back to the starting position, maintaining control."
            ) ,
            ExerciseInstruction(
                167,
                27,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to pull the weight."
            ) ,
            ExerciseInstruction(
                168,
                27,
                6,
                "Cooldown",
                "Finish the set and release the handle."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 28,
        exerciseName = "Narrow Grip Cable Row",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 3,
        gripStyle = GripStyle("Overhand", "Narrow") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                169,
                28,
                1,
                "Setup",
                "Attach a narrow-grip handle to the cable machine at chest height."
            ) ,
            ExerciseInstruction(
                170,
                28,
                2,
                "Form",
                "Stand facing the cable machine and grab the handle with an overhand grip, hands shoulder-width apart or slightly narrower."
            ) ,
            ExerciseInstruction(
                171,
                28,
                3,
                "Concentric",
                "Pull the handle toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                172,
                28,
                4,
                "Eccentric",
                "Slowly release the handle back to the starting position, maintaining control."
            ) ,
            ExerciseInstruction(
                173,
                28,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to pull the weight."
            ) ,
            ExerciseInstruction(
                174,
                28,
                6,
                "Cooldown",
                "Finish the set and release the handle."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 29,
        exerciseName = "T-Bar Row",
        equipmentList = listOf("T-Bar Row Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 4,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                175,
                29,
                1,
                "Setup",
                "Place a barbell in the landmine attachment of a T-bar row machine. Straddle the machine with your feet shoulder-width apart."
            ) ,
            ExerciseInstruction(
                176,
                29,
                2,
                "Form",
                "Bend your knees, hinge at the hips, and grasp the handles with a neutral grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                177,
                29,
                3,
                "Concentric",
                "Pull the handles toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                178,
                29,
                4,
                "Eccentric",
                "Lower the handles back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                179,
                29,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to lift the weight."
            ) ,
            ExerciseInstruction(
                180,
                29,
                6,
                "Cooldown",
                "Finish the set and release the handles."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.LowerBackPain.uid
        )
    ) , Exercise(
        uid = 31,
        exerciseName = "Machine Row",
        equipmentList = listOf("Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 6,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                187,
                31,
                1,
                "Setup",
                "Adjust the seat and chest pad of the machine to align with your body. Place your feet on the footrests."
            ) ,
            ExerciseInstruction(
                188,
                31,
                2,
                "Form",
                "Grasp the handles with a neutral grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                189,
                31,
                3,
                "Concentric",
                "Pull the handles toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                190,
                31,
                4,
                "Eccentric",
                "Release the handles back to the starting position with control."
            ) ,
            ExerciseInstruction(
                191,
                31,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to pull the weight."
            ) ,
            ExerciseInstruction(
                192,
                31,
                6,
                "Cooldown",
                "Finish the set and release the handles."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 32,
        exerciseName = "Humble Row",
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        position = 7,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 6.5f,
        techniqueCoeff = 6.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                193,
                32,
                1,
                "Setup",
                "Stand with your feet hip-width apart and slightly bend your knees. Hold a dumbbell in each hand with a neutral grip, palms facing your body."
            ) ,
            ExerciseInstruction(
                194,
                32,
                2,
                "Form",
                "Bend forward at the hips while keeping your back straight. Let your arms hang straight down, perpendicular to the floor."
            ) ,
            ExerciseInstruction(
                195,
                32,
                3,
                "Concentric",
                "Pull the dumbbells toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                196,
                32,
                4,
                "Eccentric",
                "Lower the dumbbells back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                197,
                32,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to lift the weights."
            ) ,
            ExerciseInstruction(
                198,
                32,
                6,
                "Cooldown",
                "Finish the set and place the dumbbells back on the ground."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.FreeWeightFocus.uid)

    ) , Exercise(
        uid = 33,
        exerciseName = "One Hand Dumbbell Row",
        equipmentList = listOf("Dumbbell"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 8,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                199,
                33,
                1,
                "Setup",
                "Place one knee and one hand on a bench, with your back parallel to the floor. Hold a dumbbell in your other hand with a neutral grip, arm fully extended."
            ) ,
            ExerciseInstruction(
                200,
                33,
                2,
                "Form",
                "Keep your back straight and your core engaged. Let the dumbbell hang straight down, perpendicular to the floor."
            ) ,
            ExerciseInstruction(
                201,
                33,
                3,
                "Concentric",
                "Pull the dumbbell toward your lower chest by retracting your shoulder blade and bending your elbow."
            ) ,
            ExerciseInstruction(
                202,
                33,
                4,
                "Eccentric",
                "Lower the dumbbell back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                203,
                33,
                5,
                "Form",
                "Avoid excessive rotation or twisting of your torso during the movement."
            ) ,
            ExerciseInstruction(
                204,
                33,
                6,
                "Cooldown",
                "Finish the set and place the dumbbell back on the ground."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 34,
        exerciseName = "High-Low Machine Row",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Machine"),
        isBodyWeight = false,
        position = 9,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                205,
                34,
                1,
                "Setup",
                "Adjust the seat and handles of the machine to align with your body. Sit with your feet on the footrests."
            ) ,
            ExerciseInstruction(
                206,
                34,
                2,
                "Form",
                "Grasp the handles with a neutral grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                207,
                34,
                3,
                "Concentric",
                "Pull the handles toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                208,
                34,
                4,
                "Eccentric",
                "Release the handles back to the starting position with control."
            ) ,
            ExerciseInstruction(
                209,
                34,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to pull the weight."
            ) ,
            ExerciseInstruction(
                210,
                34,
                6,
                "Cooldown",
                "Finish the set and release the handles."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 35,
        exerciseName = "Upright EZ-Bar Row",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("EZ-Bar"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.0f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                211,
                35,
                1,
                "Setup",
                "Stand with your feet hip-width apart and slightly bend your knees. Hold an EZ-bar with an underhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                212,
                35,
                2,
                "Form",
                "Bend forward at the hips while keeping your back straight. Let your arms hang straight down, perpendicular to the floor."
            ) ,
            ExerciseInstruction(
                213,
                35,
                3,
                "Concentric",
                "Pull the EZ-bar toward your lower chest by retracting your shoulder blades and bending your elbows."
            ) ,
            ExerciseInstruction(
                214,
                35,
                4,
                "Eccentric",
                "Lower the EZ-bar back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                215,
                35,
                5,
                "Form",
                "Keep your core engaged and avoid excessive swinging or using momentum to lift the weight."
            ) ,
            ExerciseInstruction(
                216,
                35,
                6,
                "Cooldown",
                "Finish the set and place the EZ-bar back on the ground."
            )
        ),
        movement = Movement.Row ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.ShoulderPain.uid
        )
    ) , Exercise(
        uid = 36,
        exerciseName = "Wide Grip PullDown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Wider than shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                221,
                36,
                1,
                "Setup",
                "Sit on a pull-down machine and adjust the knee pad to secure your lower body. Grasp the wide-grip bar with an overhand grip, wider than shoulder-width apart."
            ) ,
            ExerciseInstruction(
                222,
                36,
                2,
                "Form",
                "Keep your torso upright and your chest out. Pull the bar down toward your upper chest while keeping your elbows pointed out to the sides."
            ) ,
            ExerciseInstruction(
                223,
                36,
                3,
                "Concentric",
                "Squeeze your back muscles and pull your shoulder blades down and back as you bring the bar close to your chest."
            ) ,
            ExerciseInstruction(
                224,
                36,
                4,
                "Eccentric",
                "Slowly release the bar back up to the starting position with control."
            ) ,
            ExerciseInstruction(
                225,
                36,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                226,
                36,
                6,
                "Cooldown",
                "Finish the set and release the bar. Adjust the weight and repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 37,
        exerciseName = "Close Grip PullDown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width or narrower") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                231,
                37,
                1,
                "Setup",
                "Sit on a pull-down machine and adjust the knee pad to secure your lower body. Grasp the close-grip bar with an overhand grip, shoulder-width apart or narrower."
            ) ,
            ExerciseInstruction(
                232,
                37,
                2,
                "Form",
                "Keep your torso upright and your chest out. Pull the bar down toward your upper chest while keeping your elbows close to your sides."
            ) ,
            ExerciseInstruction(
                233,
                37,
                3,
                "Concentric",
                "Squeeze your back muscles and pull your shoulder blades down and back as you bring the bar close to your chest."
            ) ,
            ExerciseInstruction(
                234,
                37,
                4,
                "Eccentric",
                "Slowly release the bar back up to the starting position with control."
            ) ,
            ExerciseInstruction(
                235,
                37,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                236,
                37,
                6,
                "Cooldown",
                "Finish the set and release the bar. Adjust the weight and repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 38,
        exerciseName = "Neutral Grip PullDown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                241,
                38,
                1,
                "Setup",
                "Sit on a pull-down machine and adjust the knee pad to secure your lower body. Grasp the parallel grip handles with a neutral grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                242,
                38,
                2,
                "Form",
                "Keep your torso upright and your chest out. Pull the handles down toward your upper chest while keeping your elbows close to your sides."
            ) ,
            ExerciseInstruction(
                243,
                38,
                3,
                "Concentric",
                "Squeeze your back muscles and pull your shoulder blades down and back as you bring the handles close to your chest."
            ) ,
            ExerciseInstruction(
                244,
                38,
                4,
                "Eccentric",
                "Slowly release the handles back up to the starting position with control."
            ) ,
            ExerciseInstruction(
                245,
                38,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                246,
                38,
                6,
                "Cooldown",
                "Finish the set and release the handles. Adjust the weight and repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 39,
        exerciseName = "Assisted Pull Ups",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Assisted Pull-Up Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 2.0f,
        techniqueCoeff = 6.5f,
        sfr_rating = 9.5f,
        strengthPotentialRating = 2.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                251,
                39,
                1,
                "Setup",
                "Adjust the assisted pull-up machine to your desired weight. Place your knees on the pad and grasp the pull-up bar with an overhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                252,
                39,
                2,
                "Form",
                "Hang from the bar with your arms fully extended and your shoulders slightly elevated."
            ) ,
            ExerciseInstruction(
                253,
                39,
                3,
                "Concentric",
                "Pull yourself upward by flexing your elbows and driving your elbows down to engage your back muscles."
            ) ,
            ExerciseInstruction(
                254,
                39,
                4,
                "Eccentric",
                "Lower yourself back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                255,
                39,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                256,
                39,
                6,
                "Cooldown",
                "Finish the set and release the bar. Adjust the weight and repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.MachineFocus.uid,
            ExerciseFilter.CalisthenicsFocus.uid
        )
    ) , Exercise(
        uid = 40,
        exerciseName = "Assisted Chin Ups",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Assisted Pull-Up Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 3.0f,
        techniqueCoeff = 6.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 4.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                261,
                40,
                1,
                "Setup",
                "Adjust the assisted pull-up machine to your desired weight. Place your knees on the pad and grasp the pull-up bar with an underhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                262,
                40,
                2,
                "Form",
                "Hang from the bar with your arms fully extended and your shoulders slightly elevated."
            ) ,
            ExerciseInstruction(
                263,
                40,
                3,
                "Concentric",
                "Pull yourself upward by flexing your elbows and driving your elbows down to engage your back and biceps."
            ) ,
            ExerciseInstruction(
                264,
                40,
                4,
                "Eccentric",
                "Lower yourself back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                265,
                40,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                266,
                40,
                6,
                "Cooldown",
                "Finish the set and release the bar. Adjust the weight and repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.MachineFocus.uid,
            ExerciseFilter.CalisthenicsFocus.uid
        )
    ) ,

    Exercise(
        uid = 41,
        exerciseName = "Pull Ups",
        equipmentList = listOf("Pull Up Bar"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = true,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 9f,
        techniqueCoeff = 7.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.5f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                271,
                41,
                1,
                "Setup",
                "Hang from a pull-up bar with an overhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                272,
                41,
                2,
                "Form",
                "Start with your arms fully extended and your shoulders slightly elevated."
            ) ,
            ExerciseInstruction(
                273,
                41,
                3,
                "Concentric",
                "Pull yourself upward by flexing your elbows and driving your elbows down to engage your back muscles."
            ) ,
            ExerciseInstruction(
                274,
                41,
                4,
                "Eccentric",
                "Lower yourself back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                275,
                41,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                276,
                41,
                6,
                "Cooldown",
                "Finish the set and release the bar. Repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 42,
        exerciseName = "Chin Ups",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Pull Up Bar"),
        isBodyWeight = true,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 9.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                281,
                42,
                1,
                "Setup",
                "Hang from a pull-up bar with an underhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                282,
                42,
                2,
                "Form",
                "Start with your arms fully extended and your shoulders slightly elevated."
            ) ,
            ExerciseInstruction(
                283,
                42,
                3,
                "Concentric",
                "Pull yourself upward by flexing your elbows and driving your elbows down to engage your back and biceps."
            ) ,
            ExerciseInstruction(
                284,
                42,
                4,
                "Eccentric",
                "Lower yourself back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                285,
                42,
                5,
                "Form",
                "Avoid using momentum or swinging your body to complete the movement. Keep your core engaged and maintain proper form throughout the exercise."
            ) ,
            ExerciseInstruction(
                286,
                42,
                6,
                "Cooldown",
                "Finish the set and release the bar. Repeat for the desired number of sets and repetitions."
            )
        ),
        movement = Movement.VerticalPull ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 43,
        exerciseName = "Conventional Deadlift",
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        equipmentList = listOf("Barbell"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 9.0f,
        techniqueCoeff = 9.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 9.5f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                291,
                43,
                1,
                "Setup",
                "Stand with your feet shoulder-width apart and the barbell on the ground in front of you. Position your feet under the bar, keeping them parallel or slightly turned out."
            ) ,
            ExerciseInstruction(
                292,
                43,
                2,
                "Form",
                "Bend at the hips and knees, keeping your back straight and chest lifted. Grasp the bar with an overhand grip, hands shoulder-width apart or slightly wider."
            ) ,
            ExerciseInstruction(
                293,
                43,
                3,
                "Concentric",
                "Drive through your heels, lift the bar by extending your hips and knees simultaneously. Keep the bar close to your body throughout the movement."
            ) ,
            ExerciseInstruction(
                294,
                43,
                4,
                "Lockout",
                "Stand up straight with your shoulders pulled back and hips fully extended."
            ) ,
            ExerciseInstruction(
                295,
                43,
                5,
                "Eccentric",
                "Lower the bar back down to the starting position by bending at the hips and knees, maintaining a controlled descent."
            ) ,
            ExerciseInstruction(
                296,
                43,
                6,
                "Form",
                "Keep your core engaged, maintain a neutral spine, and avoid rounding your back. Use proper breathing techniques and avoid excessive jerking or bouncing of the bar."
            ) ,
            ExerciseInstruction(
                297,
                43,
                7,
                "Cooldown",
                "Finish the set and carefully place the barbell back on the ground."
            )
        ),
        movement = Movement.Deadlift ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid,
            ExerciseFilter.LowerBackPain.uid,
        )
    ) , Exercise(
        uid = 44,
        exerciseName = "Sumo Deadlift",
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        equipmentList = listOf("Barbell"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Wide") ,
        overloadCoeff = 9.0f,
        techniqueCoeff = 9.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                301,
                44,
                1,
                "Setup",
                "Stand with your feet wider than shoulder-width apart and position the barbell on the ground in front of you. Point your toes out at an angle."
            ) ,
            ExerciseInstruction(
                302,
                44,
                2,
                "Form",
                "Bend at the hips and knees, keeping your back straight and chest lifted. Grasp the barbell with an overhand grip, hands placed inside your legs."
            ) ,
            ExerciseInstruction(
                303,
                44,
                3,
                "Concentric",
                "Drive through your heels, lift the bar by extending your hips and knees simultaneously. Keep the bar close to your body throughout the movement."
            ) ,
            ExerciseInstruction(
                304,
                44,
                4,
                "Lockout",
                "Stand up straight with your shoulders pulled back and hips fully extended."
            ) ,
            ExerciseInstruction(
                305,
                44,
                5,
                "Eccentric",
                "Lower the bar back down to the starting position by bending at the hips and knees, maintaining a controlled descent."
            ) ,
            ExerciseInstruction(
                306,
                44,
                6,
                "Form",
                "Keep your core engaged, maintain a neutral spine, and avoid rounding your back. Use proper breathing techniques and avoid excessive jerking or bouncing of the bar."
            ) ,
            ExerciseInstruction(
                307,
                44,
                7,
                "Cooldown",
                "Finish the set and carefully place the barbell back on the ground."
            )
        ),
        movement = Movement.Deadlift ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid,
            ExerciseFilter.LowerBackPain.uid
        )
    ) , Exercise(
        uid = 45,
        exerciseName = "Romanian Deadlift with Dumbbells",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                311,
                45,
                1,
                "Setup",
                "Stand with your feet shoulder-width apart, holding a pair of dumbbells in front of your thighs with a neutral grip."
            ) ,
            ExerciseInstruction(
                312,
                45,
                2,
                "Form",
                "Bend at the hips, keeping your back straight and chest lifted. Allow the dumbbells to lower in front of your legs while maintaining a slight bend in your knees."
            ) ,
            ExerciseInstruction(
                313,
                45,
                3,
                "Concentric",
                "Drive through your heels and extend your hips, bringing your torso back to an upright position. Squeeze your glutes at the top."
            ) ,
            ExerciseInstruction(
                314,
                45,
                4,
                "Eccentric",
                "Lower the dumbbells back down by hinging at the hips while maintaining a slight bend in your knees."
            ) ,
            ExerciseInstruction(
                315,
                45,
                5,
                "Form",
                "Keep your core engaged, maintain a neutral spine, and avoid rounding your back. Control the movement and avoid using momentum."
            ) ,
            ExerciseInstruction(
                316,
                45,
                6,
                "Cooldown",
                "Finish the set and place the dumbbells back on the ground."
            )
        ),
        movement = Movement.Deadlift ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid,
            ExerciseFilter.LowerBackPain.uid
        )
    ) , Exercise(
        uid = 46,
        exerciseName = "Romanian Deadlift with Barbell",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Barbell"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                321,
                46,
                1,
                "Setup",
                "Stand with your feet shoulder-width apart, holding a barbell in front of your thighs with an overhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                322,
                46,
                2,
                "Form",
                "Bend at the hips, keeping your back straight and chest lifted. Allow the barbell to lower in front of your legs while maintaining a slight bend in your knees."
            ) ,
            ExerciseInstruction(
                323,
                46,
                3,
                "Concentric",
                "Drive through your heels and extend your hips, bringing your torso back to an upright position. Squeeze your glutes at the top."
            ) ,
            ExerciseInstruction(
                324,
                46,
                4,
                "Eccentric",
                "Lower the barbell back down by hinging at the hips while maintaining a slight bend in your knees."
            ) ,
            ExerciseInstruction(
                325,
                46,
                5,
                "Form",
                "Keep your core engaged, maintain a neutral spine, and avoid rounding your back. Control the movement and avoid using momentum."
            ) ,
            ExerciseInstruction(
                326,
                46,
                6,
                "Cooldown",
                "Finish the set and carefully place the barbell back on the ground."
            )
        ),
        movement = Movement.Deadlift ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.PowerliftingFocus.uid,
            ExerciseFilter.LowerBackPain.uid
        )
    ) , Exercise(
        uid = 47,
        exerciseName = "Standing Dumbbell Curl",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                331,
                47,
                1,
                "Setup",
                "Stand up straight with your feet shoulder-width apart, holding a dumbbell in each hand at arm's length, palms facing forward."
            ) ,
            ExerciseInstruction(
                332,
                47,
                2,
                "Form",
                "Keeping your upper arms stationary, exhale and curl the weights while contracting your biceps. Continue to raise the weights until your biceps are fully contracted."
            ) ,
            ExerciseInstruction(
                333,
                47,
                3,
                "Concentric",
                "Hold the contracted position for a brief pause as you squeeze your biceps. Inhale and slowly begin to lower the dumbbells back to the starting position."
            ) ,
            ExerciseInstruction(
                334,
                47,
                4,
                "Eccentric",
                "Repeat the curling motion for the prescribed number of repetitions."
            ) ,
            ExerciseInstruction(
                335,
                47,
                5,
                "Form",
                "Ensure that only your forearms move, and avoid using your shoulders or back to lift the weights. Keep your elbows close to your torso."
            ) ,
            ExerciseInstruction(
                336,
                47,
                6,
                "Cooldown",
                "Finish the set and carefully place the dumbbells back on the ground."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 48,
        exerciseName = "EZ-Bar Curl",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("EZ-Bar"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 7.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                341,
                48,
                1,
                "Setup",
                "Stand up straight with your feet shoulder-width apart, holding an EZ-bar at arm's length, palms facing forward."
            ) ,
            ExerciseInstruction(
                342,
                48,
                2,
                "Form",
                "Keeping your upper arms stationary, exhale and curl the bar while contracting your biceps. Continue to raise the bar until your biceps are fully contracted."
            ) ,
            ExerciseInstruction(
                343,
                48,
                3,
                "Concentric",
                "Hold the contracted position for a brief pause as you squeeze your biceps. Inhale and slowly begin to lower the bar back to the starting position."
            ) ,
            ExerciseInstruction(
                344,
                48,
                4,
                "Eccentric",
                "Repeat the curling motion for the prescribed number of repetitions."
            ) ,
            ExerciseInstruction(
                345,
                48,
                5,
                "Form",
                "Ensure that only your forearms move, and avoid using your shoulders or back to lift the bar. Keep your elbows close to your torso."
            ) ,
            ExerciseInstruction(
                346,
                48,
                6,
                "Cooldown",
                "Finish the set and carefully place the EZ-bar back on the ground."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) ,

    Exercise(
        uid = 49,
        exerciseName = "Incline Bicep Curl",
        equipmentList = listOf("Dumbbells"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                351,
                49,
                1,
                "Setup",
                "Adjust an incline bench to a comfortable angle (typically around 45 degrees). Sit on the bench with a dumbbell in each hand, palms facing forward."
            ) ,
            ExerciseInstruction(
                352,
                49,
                2,
                "Form",
                "Lean back on the bench and let your arms hang straight down, fully extended. Keep your elbows close to your torso."
            ) ,
            ExerciseInstruction(
                353,
                49,
                3,
                "Concentric",
                "Exhale and curl the dumbbells while contracting your biceps. Continue to raise the dumbbells until your biceps are fully contracted."
            ) ,
            ExerciseInstruction(
                354,
                49,
                4,
                "Eccentric",
                "Inhale and slowly lower the dumbbells back to the starting position with control."
            ) ,
            ExerciseInstruction(
                355,
                49,
                5,
                "Form",
                "Avoid using your shoulders or back to lift the dumbbells. Focus on isolating your biceps and maintaining proper form."
            ) ,
            ExerciseInstruction(
                356,
                49,
                6,
                "Cooldown",
                "Finish the set and carefully place the dumbbells on the floor."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 50,
        exerciseName = "Cable Bicep Curl",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                361,
                50,
                1,
                "Setup",
                "Stand facing a cable machine with the pulley set at the lowest position. Grasp the cable attachment with an underhand grip, hands shoulder-width apart."
            ) ,
            ExerciseInstruction(
                362,
                50,
                2,
                "Form",
                "Step back to create tension on the cable. Keep your elbows close to your torso and extend your arms fully."
            ) ,
            ExerciseInstruction(
                363,
                50,
                3,
                "Concentric",
                "Exhale and curl the cable attachment towards your shoulders, contracting your biceps. Keep your upper arms stationary throughout the movement."
            ) ,
            ExerciseInstruction(
                364,
                50,
                4,
                "Eccentric",
                "Inhale and slowly return the cable attachment to the starting position, fully extending your arms."
            ) ,
            ExerciseInstruction(
                365,
                50,
                5,
                "Form",
                "Maintain a slight forward lean and avoid swinging or using momentum to lift the weight. Focus on controlling the movement."
            ) ,
            ExerciseInstruction(
                366,
                50,
                6,
                "Cooldown",
                "Finish the set and release the cable attachment."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 51,
        exerciseName = "Hammer Curl",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Dumbbells"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                371,
                51,
                1,
                "Setup",
                "Stand up straight with your feet shoulder-width apart, holding a dumbbell in each hand at arm's length, palms facing your torso."
            ) ,
            ExerciseInstruction(
                372,
                51,
                2,
                "Form",
                "Keep your upper arms stationary, exhale and curl the weights while contracting your biceps. Continue to raise the weights until your biceps are fully contracted."
            ) ,
            ExerciseInstruction(
                373,
                51,
                3,
                "Concentric",
                "Hold the contracted position for a brief pause as you squeeze your biceps. Inhale and slowly begin to lower the dumbbells back to the starting position."
            ) ,
            ExerciseInstruction(
                374,
                51,
                4,
                "Eccentric",
                "Repeat the curling motion for the prescribed number of repetitions."
            ) ,
            ExerciseInstruction(
                375,
                51,
                5,
                "Form",
                "Ensure that only your forearms move, and avoid using your shoulders or back to lift the weights. Keep your elbows close to your torso."
            ) ,
            ExerciseInstruction(
                376,
                51,
                6,
                "Cooldown",
                "Finish the set and carefully place the dumbbells back on the ground."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) ,

    Exercise(
        uid = 52,
        exerciseName = "One Arm Preacher Curl",
        equipmentList = listOf("Preacher Curl Bench", "Dumbbell"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                381,
                52,
                1,
                "Setup",
                "Adjust a preacher curl bench to a comfortable height. Sit on the bench and hold a dumbbell in one hand, palms facing upward."
            ) ,
            ExerciseInstruction(
                382,
                52,
                2,
                "Form",
                "Rest your working arm against the pad of the preacher curl bench, allowing your arm to extend fully."
            ) ,
            ExerciseInstruction(
                383,
                52,
                3,
                "Concentric",
                "Exhale and curl the dumbbell upwards while contracting your biceps. Continue to raise the dumbbell until your biceps are fully contracted."
            ) ,
            ExerciseInstruction(
                384,
                52,
                4,
                "Eccentric",
                "Inhale and slowly lower the dumbbell back to the starting position with control."
            ) ,
            ExerciseInstruction(
                385,
                52,
                5,
                "Form",
                "Ensure that only your forearm moves, and avoid using your shoulder or back to lift the weight. Keep your elbow stationary against the pad."
            ) ,
            ExerciseInstruction(
                386,
                52,
                6,
                "Cooldown",
                "Finish the set and carefully place the dumbbell on the floor."
            )
        ),
        movement = Movement.BicepsCurl ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 53,
        exerciseName = "Rope Triceps Pushdown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                391,
                53,
                1,
                "Setup",
                "Attach a rope handle to a high pulley on a cable machine. Stand facing the machine with your feet shoulder-width apart. Grab the rope with an overhand grip."
            ) ,
            ExerciseInstruction(
                392,
                53,
                2,
                "Form",
                "Step back to create tension on the cable. Keep your elbows close to your torso and your upper arms stationary."
            ) ,
            ExerciseInstruction(
                393,
                53,
                3,
                "Concentric",
                "Exhale and push the rope down by extending your elbows. Continue until your elbows are fully extended."
            ) ,
            ExerciseInstruction(
                394,
                53,
                4,
                "Eccentric",
                "Inhale and slowly return the rope to the starting position by allowing your elbows to bend."
            ) ,
            ExerciseInstruction(
                395,
                53,
                5,
                "Form",
                "Keep your core engaged and avoid using your shoulders or back to assist in the movement. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                396,
                53,
                6,
                "Cooldown",
                "Finish the set and release the rope handle."
            )
        ),
        movement = Movement.TricepsPushdown ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 54,
        exerciseName = "Straight Bar Triceps Pushdown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                401,
                54,
                1,
                "Setup",
                "Attach a straight bar handle to a high pulley on a cable machine. Stand facing the machine with your feet shoulder-width apart. Grab the bar with an overhand grip."
            ) ,
            ExerciseInstruction(
                402,
                54,
                2,
                "Form",
                "Step back to create tension on the cable. Keep your elbows close to your torso and your upper arms stationary."
            ) ,
            ExerciseInstruction(
                403,
                54,
                3,
                "Concentric",
                "Exhale and push the bar down by extending your elbows. Continue until your elbows are fully extended."
            ) ,
            ExerciseInstruction(
                404,
                54,
                4,
                "Eccentric",
                "Inhale and slowly return the bar to the starting position by allowing your elbows to bend."
            ) ,
            ExerciseInstruction(
                405,
                54,
                5,
                "Form",
                "Maintain a slight forward lean and avoid using your shoulders or back to assist in the movement. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                406,
                54,
                6,
                "Cooldown",
                "Finish the set and release the bar handle."
            )
        ),
        movement = Movement.TricepsPushdown ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 55,
        exerciseName = "V-Bar Triceps Pushdown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                411,
                55,
                1,
                "Setup",
                "Attach a V-bar handle to a high pulley on a cable machine. Stand facing the machine with your feet shoulder-width apart. Grab the V-bar with an overhand grip."
            ) ,
            ExerciseInstruction(
                412,
                55,
                2,
                "Form",
                "Step back to create tension on the cable. Keep your elbows close to your torso and your upper arms stationary."
            ) ,
            ExerciseInstruction(
                413,
                55,
                3,
                "Concentric",
                "Exhale and push the V-bar down by extending your elbows. Continue until your elbows are fully extended."
            ) ,
            ExerciseInstruction(
                414,
                55,
                4,
                "Eccentric",
                "Inhale and slowly return the V-bar to the starting position by allowing your elbows to bend."
            ) ,
            ExerciseInstruction(
                415,
                55,
                5,
                "Form",
                "Maintain a slight forward lean and avoid using your shoulders or back to assist in the movement. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                416,
                55,
                6,
                "Cooldown",
                "Finish the set and release the V-bar handle."
            )
        ),
        movement = Movement.TricepsPushdown ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 56,
        exerciseName = "Reverse Grip Triceps Pushdown",
        equipmentList = listOf("Cable Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Underhand", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                421,
                56,
                1,
                "Setup",
                "Attach a straight bar handle to a high pulley on a cable machine. Stand facing away from the machine with your feet shoulder-width apart. Grab the bar with an underhand grip."
            ) ,
            ExerciseInstruction(
                422,
                56,
                2,
                "Form",
                "Step forward to create tension on the cable. Keep your elbows close to your torso and your upper arms stationary."
            ) ,
            ExerciseInstruction(
                423,
                56,
                3,
                "Concentric",
                "Exhale and push the bar down by extending your elbows. Continue until your elbows are fully extended."
            ) ,
            ExerciseInstruction(
                424,
                56,
                4,
                "Eccentric",
                "Inhale and slowly return the bar to the starting position by allowing your elbows to bend."
            ) ,
            ExerciseInstruction(
                425,
                56,
                5,
                "Form",
                "Maintain a slight forward lean and avoid using your shoulders or back to assist in the movement. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                426,
                56,
                6,
                "Cooldown",
                "Finish the set and release the bar handle."
            )
        ),
        movement = Movement.TricepsPushdown ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 57,
        exerciseName = "Single-Arm Cable Triceps Pushdown",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 8.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                431,
                57,
                1,
                "Setup",
                "Attach a handle to a high pulley on a cable machine. Stand facing the machine with your feet shoulder-width apart. Grab the handle with an overhand grip."
            ) ,
            ExerciseInstruction(
                432,
                57,
                2,
                "Form",
                "Step back to create tension on the cable. Keep your elbow close to your torso and your upper arm stationary."
            ) ,
            ExerciseInstruction(
                433,
                57,
                3,
                "Concentric",
                "Exhale and push the handle down by extending your elbow. Continue until your arm is fully extended."
            ) ,
            ExerciseInstruction(
                434,
                57,
                4,
                "Eccentric",
                "Inhale and slowly return the handle to the starting position by allowing your elbow to bend."
            ) ,
            ExerciseInstruction(
                435,
                57,
                5,
                "Form",
                "Maintain proper posture and avoid using your shoulder or back to assist in the movement. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                436,
                57,
                6,
                "Cooldown",
                "Finish the set and release the handle."
            )
        ),
        movement = Movement.TricepsPushdown ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 59,
        exerciseName = "Seated Dumbbell Triceps Extension",
        equipmentList = listOf("Dumbbell"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                451,
                59,
                1,
                "Setup",
                "Sit on a bench or chair with your feet flat on the floor. Hold a dumbbell with both hands, palms facing up. Raise the dumbbell overhead, fully extending your arms."
            ) ,
            ExerciseInstruction(
                452,
                59,
                2,
                "Form",
                "Bend your elbows and lower the dumbbell behind your head, keeping your upper arms close to your ears."
            ) ,
            ExerciseInstruction(
                453,
                59,
                3,
                "Concentric",
                "Extend your elbows and raise the dumbbell back to the starting position."
            ) ,
            ExerciseInstruction(
                454,
                59,
                4,
                "Eccentric",
                "Slowly lower the dumbbell behind your head by bending your elbows."
            ) ,
            ExerciseInstruction(
                455,
                59,
                5,
                "Form",
                "Maintain a stable seated position with good posture. Focus on contracting your triceps and avoid swinging or using momentum."
            ) ,
            ExerciseInstruction(
                456,
                59,
                6,
                "Cooldown",
                "Finish the set and lower the dumbbell back down to your sides."
            )
        ),
        movement = Movement.TricepsExtension ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 60,
        exerciseName = "Skull Crusher",
        equipmentList = listOf("Barbell", "Ez-Bar"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                461,
                60,
                1,
                "Setup",
                "Lie down on a flat bench with a barbell or EZ-bar loaded with weight. Extend your arms straight up over your chest, palms facing each other."
            ) ,
            ExerciseInstruction(
                462,
                60,
                2,
                "Form",
                "Bend your elbows and lower the barbell toward your forehead, keeping your upper arms stationary."
            ) ,
            ExerciseInstruction(
                463,
                60,
                3,
                "Concentric",
                "Extend your elbows and raise the barbell back to the starting position."
            ) ,
            ExerciseInstruction(
                464,
                60,
                4,
                "Eccentric",
                "Lower the barbell behind your head by bending your elbows."
            ) ,
            ExerciseInstruction(
                465,
                60,
                5,
                "Form",
                "Keep your elbows pointing up, close to your head, and avoid excessive flaring of your elbows. Focus on contracting your triceps."
            ) ,
            ExerciseInstruction(
                466,
                60,
                6,
                "Cooldown",
                "Finish the set and carefully rack the barbell or place the EZ-bar back on the floor."
            )
        ),
        movement = Movement.TricepsExtension ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 63,
        exerciseName = "Machine Triceps Extension",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = listOf("Triceps Extension Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                491,
                63,
                1,
                "Setup",
                "Adjust the seat and handles of the triceps extension machine to fit your body. Sit down and position your upper arms against the pads, gripping the handles with an overhand grip."
            ) ,
            ExerciseInstruction(
                492,
                63,
                2,
                "Form",
                "Extend your arms and fully straighten your elbows, keeping your upper arms stationary and against the pads."
            ) ,
            ExerciseInstruction(
                493,
                63,
                3,
                "Concentric",
                "Exhale and bend your elbows to lower the handles down towards your body, maintaining control."
            ) ,
            ExerciseInstruction(
                494,
                63,
                4,
                "Eccentric",
                "Inhale and extend your elbows to raise the handles back up to the starting position."
            ) ,
            ExerciseInstruction(
                495,
                63,
                5,
                "Form",
                "Maintain a stable seated position and focus on contracting your triceps throughout the movement."
            ) ,
            ExerciseInstruction(
                496,
                63,
                6,
                "Cooldown",
                "Finish the set and carefully release the handles."
            )
        ),
        movement = Movement.TricepsExtension ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 64,
        exerciseName = "Front Squat",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Barbell", "Power Rack"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Clean Grip", "Shoulder-width") ,
        overloadCoeff = 9.0f,
        techniqueCoeff = 5.0f,
        sfr_rating = 5.5f,
        strengthPotentialRating = 8f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                511,
                64,
                1,
                "Setup",
                "Position the barbell on a power rack at about shoulder height. Stand facing the barbell and approach it, stepping under it and positioning it across the front of your shoulders, with your elbows pointing forward and upper arms parallel to the floor."
            ) ,
            ExerciseInstruction(
                512,
                64,
                2,
                "Form",
                "Maintain a tall posture with your chest up, core braced, and feet shoulder-width apart."
            ) ,
            ExerciseInstruction(
                513,
                64,
                3,
                "Concentric",
                "Begin by descending into a squat by pushing your hips back and bending your knees, keeping your torso upright. Lower until your thighs are parallel to the ground or slightly below."
            ) ,
            ExerciseInstruction(
                514,
                64,
                4,
                "Eccentric",
                "Drive through your heels and extend your hips and knees to return to the starting position."
            ) ,
            ExerciseInstruction(
                515,
                64,
                5,
                "Form",
                "Keep your elbows high and maintain a tight grip on the barbell throughout the movement. Focus on engaging your quadriceps and maintaining proper form."
            ) ,
            ExerciseInstruction(
                516,
                64,
                6,
                "Cooldown",
                "Finish the set and carefully rack the barbell."
            )
        ),
        movement = Movement.Squat ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.LowerBackPain.uid,
            ExerciseFilter.KneePain.uid
        )
    ) , Exercise(
        uid = 65,
        exerciseName = "Back Squat",
        exerciseCategory = Exercise.Companion.ExerciseCategory.PrimaryCompound ,
        equipmentList = listOf("Barbell", "Power Rack"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Shoulder-width") ,
        overloadCoeff = 10.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 7f,
        strengthPotentialRating = 10.0f,
        rangeOfMotion = 9.0f,
        instructionList = listOf(
            ExerciseInstruction(
                521,
                65,
                1,
                "Setup",
                "Position the barbell on a power rack at about shoulder height. Approach the bar and grip it slightly wider than shoulder-width apart, with your palms facing forward."
            ) ,
            ExerciseInstruction(
                522,
                65,
                2,
                "Form",
                "Stand with your feet shoulder-width apart and your toes pointing slightly outward. Position the barbell across your upper back and shoulders, resting it on the meaty part of your traps."
            ) ,
            ExerciseInstruction(
                523,
                65,
                3,
                "Concentric",
                "Initiate the squat by bending at the hips and knees, keeping your chest up and core braced. Lower your body until your thighs are parallel to the ground or slightly below."
            ) ,
            ExerciseInstruction(
                524,
                65,
                4,
                "Eccentric",
                "Drive through your heels and extend your hips and knees to return to the starting position."
            ) ,
            ExerciseInstruction(
                525,
                65,
                5,
                "Form",
                "Maintain a neutral spine throughout the movement and avoid excessive forward lean. Focus on engaging your quadriceps, hamstrings, and glutes."
            ) ,
            ExerciseInstruction(
                526,
                65,
                6,
                "Cooldown",
                "Finish the set and carefully rack the barbell."
            )
        ),
        movement = Movement.Squat ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.LowerBackPain.uid,
            ExerciseFilter.KneePain.uid,
            ExerciseFilter.PowerliftingFocus.uid
        )
    ) , Exercise(
        uid = 66,
        exerciseName = "Hack Squat",
        equipmentList = listOf("Hack Squat Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 7.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                531,
                66,
                1,
                "Setup",
                "Adjust the seat and back pad of the hack squat machine to fit your body. Stand with your back against the back pad and position your shoulders under the shoulder pads."
            ) ,
            ExerciseInstruction(
                532,
                66,
                2,
                "Form",
                "Place your feet hip-width apart on the platform, with your toes slightly pointed outward."
            ) ,
            ExerciseInstruction(
                533,
                66,
                3,
                "Concentric",
                "Unhook the safeties and lower yourself by bending your knees and hips until your thighs are parallel to the platform or slightly below."
            ) ,
            ExerciseInstruction(
                534,
                66,
                4,
                "Eccentric",
                "Push through your heels and extend your knees and hips to return to the starting position."
            ) ,
            ExerciseInstruction(
                535,
                66,
                5,
                "Form",
                "Maintain a stable position with your core engaged throughout the movement. Focus on engaging your quadriceps and glutes."
            ) ,
            ExerciseInstruction(
                536,
                66,
                6,
                "Cooldown",
                "Finish the set and re-hook the safeties."
            )
        ),
        movement = Movement.Squat ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 67,
        exerciseName = "Leg Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Leg Press Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                541,
                67,
                1,
                "Setup",
                "Adjust the seat and footplate of the leg press machine to fit your body. Sit down and place your feet shoulder-width apart on the footplate, with your knees bent."
            ) ,
            ExerciseInstruction(
                542,
                67,
                2,
                "Form",
                "Push the footplate away by extending your knees and hips until your legs are fully extended."
            ) ,
            ExerciseInstruction(
                543,
                67,
                3,
                "Concentric",
                "Bend your knees and lower the footplate towards your body, maintaining control."
            ) ,
            ExerciseInstruction(
                544,
                67,
                4,
                "Eccentric",
                "Extend your knees and push the footplate away to return to the starting position."
            ) ,
            ExerciseInstruction(
                545,
                67,
                5,
                "Form",
                "Maintain a stable position on the seat and focus on engaging your quadriceps and glutes."
            ) ,
            ExerciseInstruction(
                546,
                67,
                6,
                "Cooldown",
                "Finish the set and release the footplate."
            )
        ),
        movement = Movement.Squat ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) , Exercise(
        uid = 68,
        exerciseName = "Farmers Walk",
        equipmentList = listOf("Dumbbells"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                551,
                68,
                1,
                "Setup",
                "Stand with your feet hip-width apart, holding a pair of heavy dumbbells or kettlebells by your sides."
            ) ,
            ExerciseInstruction(
                552,
                68,
                2,
                "Form",
                "Maintain an upright posture and engage your core. Walk forward while maintaining a controlled grip on the weights."
            ) ,
            ExerciseInstruction(
                553,
                68,
                3,
                "Concentric",
                "Continue walking for a set distance or time, keeping your shoulders stabilized and your steps deliberate."
            ) ,
            ExerciseInstruction(
                554,
                68,
                4,
                "Cooldown",
                "Finish the set and safely set down the weights."
            )
        ),
        movement = Movement.Lunge ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.KneePain.uid
        )
    ) , Exercise(
        uid = 69,
        exerciseName = "Bulgarian Split Squat",
        equipmentList = listOf("Barbell", "Dumbbell"),
        isBodyWeight = false,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                561,
                69,
                1,
                "Setup",
                "Stand with your back to a bench or elevated platform, holding a barbell or dumbbells by your sides."
            ) ,
            ExerciseInstruction(
                562,
                69,
                2,
                "Form",
                "Place the top of your right foot on the bench behind you, with your left foot planted firmly on the ground. Take a step forward to create distance."
            ) ,
            ExerciseInstruction(
                563,
                69,
                3,
                "Concentric",
                "Bend both knees to lower your body, allowing your back knee to approach the ground. Keep your torso upright and your front knee in line with your ankle."
            ) ,
            ExerciseInstruction(
                564,
                69,
                4,
                "Eccentric",
                "Push through your front heel to extend your legs and return to the starting position."
            ) ,
            ExerciseInstruction(
                565,
                69,
                5,
                "Form",
                "Perform the desired number of repetitions on one leg before switching to the other leg."
            ) ,
            ExerciseInstruction(
                566,
                69,
                6,
                "Cooldown",
                "Finish the set and carefully set down the weights."
            )
        ),
        movement = Movement.Lunge ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.KneePain.uid
        )
    ) , Exercise(
        uid = 70,
        exerciseName = "Alternating Lunges",
        equipmentList = listOf("Dumbbell", "Barbell"),
        isBodyWeight = false,
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        position = 10,
        gripStyle = GripStyle("Neutral", "Shoulder-width") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                571,
                70,
                1,
                "Setup",
                "Stand with your feet hip-width apart, holding a dumbbell or barbell by your sides."
            ) ,
            ExerciseInstruction(
                572,
                70,
                2,
                "Form",
                "Take a step forward with your right leg and lower your body into a lunge, bending both knees to approximately 90-degree angles."
            ) ,
            ExerciseInstruction(
                573,
                70,
                3,
                "Concentric",
                "Push through your right heel to return to the starting position."
            ) ,
            ExerciseInstruction(
                574,
                70,
                4,
                "Eccentric",
                "Repeat the movement on your left leg, alternating legs with each repetition."
            ) ,
            ExerciseInstruction(
                575,
                70,
                5,
                "Form",
                "Keep your torso upright, engage your core, and maintain control throughout the exercise."
            ) ,
            ExerciseInstruction(
                576,
                70,
                6,
                "Cooldown",
                "Finish the set and safely set down the weights."
            )
        ),
        movement = Movement.Lunge ,
        filters = listOf(
            ExerciseFilter.CompoundFocus.uid,
            ExerciseFilter.FreeWeightFocus.uid,
            ExerciseFilter.KneePain.uid
        )
    ) , Exercise(
        uid = 71,
        exerciseName = "Glute Kickback",
        equipmentList = listOf("Cable Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                721,
                71,
                1,
                "Setup",
                "Attach an ankle cuff to a low pulley on a cable machine. Face the machine and position yourself a few feet away, with your feet hip-width apart."
            ) ,
            ExerciseInstruction(
                722,
                71,
                2,
                "Form",
                "Engage your core and maintain a slight bend in your standing leg throughout the exercise."
            ) ,
            ExerciseInstruction(
                723,
                71,
                3,
                "Movement",
                "With the ankle cuff securely attached to your working leg, kick back your leg, keeping it straight, until you feel a squeeze in your glutes."
            ) ,
            ExerciseInstruction(
                724,
                71,
                4,
                "Return",
                "Slowly lower your leg back to the starting position."
            ) ,
            ExerciseInstruction(
                725,
                71,
                5,
                "Switch",
                "Complete the desired number of repetitions and repeat the exercise on the opposite leg."
            )
        ),
        movement = Movement.GlutesKickBacks ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
    ) , Exercise(
        uid = 72,
        exerciseName = "Hip Thrust",
        equipmentList = listOf("Bench", "Barbell"),
        isBodyWeight = false,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 9.0f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                731,
                72,
                1,
                "Setup",
                "Sit with your upper back against a bench, knees bent, and feet flat on the ground. Place a barbell over your hips and grip it with an overhand grip."
            ) ,
            ExerciseInstruction(
                732,
                72,
                2,
                "Form",
                "Engage your core and maintain a neutral spine throughout the exercise."
            ) ,
            ExerciseInstruction(
                733,
                72,
                3,
                "Movement",
                "Press through your heels and lift your hips off the ground until your body forms a straight line from your knees to your shoulders."
            ) ,
            ExerciseInstruction(
                734,
                72,
                4,
                "Squeeze",
                "Squeeze your glutes at the top of the movement."
            ) ,
            ExerciseInstruction(
                735,
                72,
                5,
                "Lower",
                "Lower your hips back down to the starting position, maintaining control."
            ) ,
            ExerciseInstruction(
                736,
                72,
                6,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.HipThrusts ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.FreeWeightFocus.uid)
    ) , Exercise(
        uid = 73,
        exerciseName = "Hanging Leg Raise",
        equipmentList = listOf("Pull-up Bar"),
        isBodyWeight = true,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                731,
                73,
                1,
                "Setup",
                "Hang from a pull-up bar with an overhand grip, shoulder-width apart."
            ) ,
            ExerciseInstruction(
                732,
                73,
                2,
                "Form",
                "Engage your core and maintain a slight bend in your knees throughout the exercise."
            ) ,
            ExerciseInstruction(
                733,
                73,
                3,
                "Movement",
                "Raise your legs by flexing your hips and knees until your thighs are parallel to the ground."
            ) ,
            ExerciseInstruction(
                734,
                73,
                4,
                "Lower",
                "Lower your legs back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                735,
                73,
                5,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.LegRaise ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 74,
        exerciseName = "Flat Leg Raise",
        equipmentList = emptyList(),
        isBodyWeight = true,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                741,
                74,
                1,
                "Setup",
                "Lie flat on your back on a mat with your legs fully extended."
            ) ,
            ExerciseInstruction(
                742,
                74,
                2,
                "Form",
                "Place your hands by your sides or under your glutes for support."
            ) ,
            ExerciseInstruction(
                743,
                74,
                3,
                "Movement",
                "Raise your legs by flexing your hips and bending your knees until your thighs are perpendicular to the ground."
            ) ,
            ExerciseInstruction(
                744,
                74,
                4,
                "Lower",
                "Slowly lower your legs back down to the starting position."
            ) ,
            ExerciseInstruction(
                745,
                74,
                5,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.LegRaise ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 75,
        exerciseName = "Flat Crunch",
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        equipmentList = emptyList(),
        isBodyWeight = true,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.5f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                751,
                75,
                1,
                "Setup",
                "Lie flat on your back on a mat with your knees bent and feet flat on the ground."
            ) ,
            ExerciseInstruction(
                752,
                75,
                2,
                "Form",
                "Place your hands by your temples or cross them over your chest."
            ) ,
            ExerciseInstruction(
                753,
                75,
                3,
                "Movement",
                "Curl your upper body forward, lifting your shoulder blades off the ground."
            ) ,
            ExerciseInstruction(
                754,
                75,
                4,
                "Contract",
                "Squeeze your abdominal muscles at the top of the movement."
            ) ,
            ExerciseInstruction(
                755,
                75,
                5,
                "Lower",
                "Lower your upper body back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                756,
                75,
                6,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.Crunch ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 76,
        exerciseName = "Weighted Crunch",
        equipmentList = listOf("Weight Plate"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                761,
                76,
                1,
                "Setup",
                "Lie flat on your back on a mat with your knees bent and feet flat on the ground. Hold a weight plate against your chest."
            ) ,
            ExerciseInstruction(
                762,
                76,
                2,
                "Form",
                "Place your hands by your temples or cross them over your chest."
            ) ,
            ExerciseInstruction(
                763,
                76,
                3,
                "Movement",
                "Curl your upper body forward, lifting your shoulder blades off the ground."
            ) ,
            ExerciseInstruction(
                764,
                76,
                4,
                "Contract",
                "Squeeze your abdominal muscles at the top of the movement."
            ) ,
            ExerciseInstruction(
                765,
                76,
                5,
                "Lower",
                "Lower your upper body back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                766,
                76,
                6,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.Crunch ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) , Exercise(
        uid = 77,
        exerciseName = "Decline Crunch",
        equipmentList = listOf("Decline Bench"),
        isBodyWeight = true,
        position = 10,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                771,
                77,
                1,
                "Setup",
                "Position yourself on a decline bench with your feet secured under the foot pads."
            ) ,
            ExerciseInstruction(
                772,
                77,
                2,
                "Form",
                "Place your hands by your temples or cross them over your chest."
            ) ,
            ExerciseInstruction(
                773,
                77,
                3,
                "Movement",
                "Curl your upper body forward, lifting your shoulder blades off the bench."
            ) ,
            ExerciseInstruction(
                774,
                77,
                4,
                "Contract",
                "Squeeze your abdominal muscles at the top of the movement."
            ) ,
            ExerciseInstruction(
                775,
                77,
                5,
                "Lower",
                "Lower your upper body back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                776,
                77,
                6,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.Crunch ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    ) ,
    Exercise(
        uid = 78,
        exerciseName = "Smith Machine Chest Press",
        equipmentList = listOf("Smith Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 7.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 7f,
        rangeOfMotion = 8.0f,
        instructionList = listOf(
            ExerciseInstruction(
                781,
                78,
                1,
                "Setup",
                "Position yourself on a flat bench underneath the Smith machine bar. Grip the bar slightly wider than shoulder-width apart."
            ) ,
            ExerciseInstruction(
                782,
                78,
                2,
                "Form",
                "Unrack the bar and lower it towards your chest, keeping your elbows slightly flared out."
            ) ,
            ExerciseInstruction(
                783,
                78,
                3,
                "Press",
                "Push the bar back up to the starting position, fully extending your arms."
            ) ,
            ExerciseInstruction(
                784,
                78,
                4,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) ,

    Exercise(
        uid = 79,
        exerciseName = "Close-Grip Bench Press",
        equipmentList = listOf("Barbell"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("Overhand", "Close") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.5f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8f,
        rangeOfMotion = 7.5f,
        instructionList = listOf(
            ExerciseInstruction(
                791,
                79,
                1,
                "Setup",
                "Lie flat on a bench and grip the barbell with an overhand grip, hands closer than shoulder-width apart."
            ) ,
            ExerciseInstruction(
                792,
                79,
                2,
                "Form",
                "Lower the barbell to your chest, keeping your elbows tucked close to your sides."
            ) ,
            ExerciseInstruction(
                793,
                79,
                3,
                "Press",
                "Push the barbell back up to the starting position, fully extending your arms."
            ) ,
            ExerciseInstruction(
                794,
                79,
                4,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.FreeWeightFocus.uid),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
    ) ,

    Exercise(
        uid = 80,
        exerciseName = "Machine Incline Chest Press",
        equipmentList = listOf("Chest Press Machine"),
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 7f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                801,
                80,
                1,
                "Setup",
                "Adjust the seat and backrest of the machine to a comfortable position. Grip the handles with an overhand grip."
            ) ,
            ExerciseInstruction(
                802,
                80,
                2,
                "Form",
                "Push the handles forward, extending your arms fully."
            ) ,
            ExerciseInstruction(
                803,
                80,
                3,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) ,

    Exercise(
        uid = 81,
        exerciseName = "Machine Decline Chest Press",
        exerciseCategory = Exercise.Companion.ExerciseCategory.SecondaryCompound ,
        equipmentList = listOf("Chest Press Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.5f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 7f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                811,
                81,
                1,
                "Setup",
                "Adjust the seat and backrest of the machine to a comfortable position. Grip the handles with an overhand grip."
            ) ,
            ExerciseInstruction(
                812,
                81,
                2,
                "Form",
                "Push the handles forward, extending your arms fully."
            ) ,
            ExerciseInstruction(
                813,
                81,
                3,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.ChestPress ,
        filters = listOf(ExerciseFilter.CompoundFocus.uid, ExerciseFilter.MachineFocus.uid)
    ) ,
    Exercise(
        uid = 82,
        exerciseName = "Machine Calves Raise",
        equipmentList = listOf("Calf Raise Machine"),
        isBodyWeight = false,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 8.0f,
        techniqueCoeff = 8.0f,
        sfr_rating = 9.0f,
        strengthPotentialRating = 8.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                811,
                82,
                1,
                "Setup",
                "Adjust the machine to a comfortable position. Place your shoulders under the pads and position your toes on the platform."
            ) ,
            ExerciseInstruction(
                812,
                82,
                2,
                "Form",
                "Raise your heels by extending your ankles as high as possible."
            ) ,
            ExerciseInstruction(
                813,
                82,
                3,
                "Lower",
                "Lower your heels back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                814,
                82,
                4,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.CalvesRaise ,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.MachineFocus.uid)
    ) ,

    Exercise(
        uid = 83,
        exerciseName = "Bodyweight Calves Raise",
        equipmentList = listOf("None"),
        isBodyWeight = true,
        position = 10,
        gripStyle = GripStyle("", "") ,
        overloadCoeff = 7.0f,
        techniqueCoeff = 7.5f,
        sfr_rating = 8.0f,
        strengthPotentialRating = 7.5f,
        rangeOfMotion = 8.5f,
        instructionList = listOf(
            ExerciseInstruction(
                821,
                83,
                1,
                "Setup",
                "Stand on the edge of a step or platform with your heels hanging off and your toes on the step."
            ) ,
            ExerciseInstruction(
                822,
                83,
                2,
                "Form",
                "Raise your heels by extending your ankles as high as possible."
            ) ,
            ExerciseInstruction(
                823,
                83,
                3,
                "Lower",
                "Lower your heels back down to the starting position with control."
            ) ,
            ExerciseInstruction(
                824,
                83,
                4,
                "Repeat",
                "Complete the desired number of repetitions."
            )
        ),
        movement = Movement.CalvesRaise ,
        exerciseCategory = Exercise.Companion.ExerciseCategory.Isolation ,
        filters = listOf(ExerciseFilter.Isolation.uid, ExerciseFilter.CalisthenicsFocus.uid)
    )
)