package com.example.workoutcompanion.exercise

object ExerciseCollection {
    val COMPOUND = 0
    val ISOLATION = 1
    val chestExercises = listOf(
        Exercise(
            name = "Flat Bench Press",
            description = "One of the best exercise to build strength . The barbell gives you a more stable frame to help you push more weight , which gives you more bang for your bucks ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 5f,
            coeffTechnique = 8f,
            coeffRangeOfMotion = 4f,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Normal,
                ExerciseGripType.Normal
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.Wide,
                ExerciseGripWidth.ShoulderWidth
            ),
            position = ExercisePosition.Flat.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Barbell,
                ExerciseEquipment.Bench
            )
        ),
        Exercise(
            name = "Incline Bench Press",
            description = "Best exercise to develop the Upper Chest . Due to it's incline angle , the tension generated alings better with upper pectoral muscle fibers which helps hypertofy . Front Delts are activated more than in the flat variant ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.UpperPec,
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 5f,
            coeffTechnique = 7f,
            coeffRangeOfMotion = 4f,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Normal,
                ExerciseGripType.Normal
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.Wide,
                ExerciseGripWidth.ShoulderWidth
            ),
            position = ExercisePosition.Incline.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Barbell,
                ExerciseEquipment.Bench
            )
        ),
        Exercise(
            name = "Decline Bench Press",
            description = "Best Exercise to target the Lower Pectoral . Because you don't use leg drive (feet of the floor, so you can't push through them) , you develop raw strength in your chest as well as minimizing front delts activation  ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.UpperPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 9f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 7f,
            coeffRangeOfMotion = 4f,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Normal,
                ExerciseGripType.Normal
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.Wide,
                ExerciseGripWidth.ShoulderWidth
            ),
            position = ExercisePosition.Decline.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Barbell,
                ExerciseEquipment.Bench
            )
        ),
        Exercise(
            name = "Flat Dumbbell Press",
            description = "Allows a wider range of motion which increases the contraction of the pecs.Because the dumbbells are less stable than barbells , shoulders have to work harder to stabilize the movement",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 6f,
            coeffRangeOfMotion = 5f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Flat.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
                ExerciseEquipment.Bench
            )
        ),
        Exercise(
            name = "Incline Dumbbell Press",
            description = "Allows a wider range of motion which increases the contraction of the upper-pecs.Because the dumbbells are less stable than barbells, you have to use less weight and the shoulders have to work harder to stabilize the movement",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 6f,
            coeffRangeOfMotion = 5f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Incline.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
                ExerciseEquipment.Bench
            )
        ),
        Exercise(
            name = "Seated Chest Press",
            description = "The machine will teach you the correct movement pattern and keep you safe from any injuries and the increased stability will increase hipertrofy . The strength you gain from this exercise will not have much carry over to the other exercises",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.FrontDelt,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial,
                MuscleGroupsExpanded.TricepsLateral
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 3f,
            coeffTechnique = 3f,
            coeffRangeOfMotion = 4f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Seated.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.ChestMachine
            )
        ),
        Exercise(
            name = "Low to High Chest Fly ",
            description = "The fly compliments very well your chest pressing movements , being one of the best accessory movements for the chest . This exercise allows a tremendous range of motion and great tension that aligns very well with the muscle fibers which other exercises lack . This one is more effective at targeting the lower and middle pec",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.UpperPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 3f,
            coeffStrenghtPotential = 2f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 10f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "High to Low Chest Fly ",
            description = "The fly compliments very well your chest pressing movements , being one of the best accessory movements for the chest . This exercise allows a tremendous range of motion and great tension that aligns very well with the muscle fibers which other exercises lack . This one is more effective at targeting the upper and middle pec",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 3f,
            coeffStrenghtPotential = 2f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 10f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "Body-Weight Push Ups ",
            description = "The Push Up is one of the most popular exercises. This exercise is great building strength and muscles for beginners. The downside of this exercise is the fact that the more you do the easier it gets which will give you less results in the long run  ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            coeffOverloading = 4f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 2f,
            coeffTechnique = 3f,
            coeffRangeOfMotion = 5f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "Body-Weight Chest Dips ",
            description = "The Chest Dip is a wise choice if you want to build an aesthetic triceps . This exercise is great building strength and muscle for beginners. The downside of this exercise is the fact that the more you do the easier it gets which will give you less results in the long run  ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Chest.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Chest.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Chest),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.FrontDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.LowerPec,
                MuscleGroupsExpanded.LowerPec
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            coeffOverloading = 4f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 6f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        )
    )
    val shoulderExercises = listOf(
        Exercise(
            name = "Overhead Barbell Press ",
            description = "AKA Military Press, is one of the best options to build huge shoulders. This movement is great because it targets the front-delt and the lateral-delt is involved to stabilize the movement. Because it is performed upright it requires a lot of core strength and may cause lower back pain ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.FrontDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.LateralDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LateralDelt,
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.TricepsLong
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 5f,
            coeffTechnique = 8f,
            coeffRangeOfMotion = 6f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Barbell,
                ExerciseEquipment.SquatRack
            )
        ),
        Exercise(
            name = "Seated Dumbbell Press ",
            description = "This exercise is as effective as the OverheadPress , but with less weight and helps you if you have an imbalance in the shoulders. This movement is great because it targets the front-delt plus the lateral-delt is involved to stabilize the movement. Because it is performed seated it requires less core strength and will not cause lower back pain ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.FrontDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.LateralDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LateralDelt,
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.TricepsLong
            ),
            coeffOverloading = 9f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 5f,
            coeffTechnique = 8f,
            coeffRangeOfMotion = 6f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Seated.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
                ExerciseEquipment.Seat
            )
        ),
        Exercise(
            name = "Smith Machine Shoulder Press ",
            description = "The Smith Machine will be more stable which is ideal if you are prone to shoulder pain. Because the bar follows a fix path , the lateral delt will be less involved in the stabilization of the movement  ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.FrontDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.LateralDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LateralDelt,
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.TricepsLong
            ),
            coeffOverloading = 9f,
            coeffMusclesInvolved = 5f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 4f,
            coeffRangeOfMotion = 7f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Seated.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.SmithMachine,
                ExerciseEquipment.Seat
            )
        ),
        Exercise(
            name = "Arnold Press ",
            description = "This is an old-school exercise that is very popular. It's gimmicky movement pattern allows the rear delts to be involved in the movement , which helps if your rear delts need more attention. ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.FrontDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.LateralDelts,
                MuscleGroupsSimple.RearDelts,
                MuscleGroupsSimple.Triceps
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.LateralDelt,
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.TricepsLong
            ),
            coeffOverloading = 9f,
            coeffMusclesInvolved = 6f,
            coeffStrenghtPotential = 5f,
            coeffTechnique = 8f,
            coeffRangeOfMotion = 7f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Seated.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
                ExerciseEquipment.Seat
            )
        ),
        Exercise(
            name = "Standing Dumbbell Lateral Fly ",
            description = "This is a must in any workout if you are looking to build that wide frame . Even if the movement looks simple ,  make sure that you control the movement and the lateral delts are working not your traps , by bending a little bit forward",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.LateralDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.LateralDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.RearDelts
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.LateralDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.RearDelt,
            ),
            coeffOverloading = 7f,
            coeffMusclesInvolved = 3f,
            coeffStrenghtPotential = 2f,
            coeffTechnique = 6f,
            coeffRangeOfMotion = 7f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
            )
        ),
        Exercise(
            name = "Standing Cable Lateral Fly ",
            description = "This is a must in any workout if you are looking to build that wide frame . The constant tension of the pulley will generate more stress in the muscle which the dumbbell version lacks . You will have to train each arm individually ",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.LateralDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.LateralDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.RearDelts
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.LateralDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.RearDelt,
            ),
            coeffOverloading = 7f,
            coeffMusclesInvolved = 3f,
            coeffStrenghtPotential = 2f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 9f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.CablePulley,
            )
        ),
        Exercise(
            name = "Cable Reverse Fly  ",
            description = "This is one of the best exercises to target the rear delts . The movement aligns very well with the muscle fiber and the constant tension from the pulley is a recipe for great gains",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.RearDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.RearDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Back
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.RearDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,

                ),
            coeffOverloading = 7f,
            coeffMusclesInvolved = 4f,
            coeffStrenghtPotential = 3f,
            coeffTechnique = 6f,
            coeffRangeOfMotion = 10f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.CablePulley,
            )
        ),
        Exercise(
            name = "Peck-Deck Reverse Fly ",
            description = "The peck-deck gives you more stability while performing the exercise which will help you to concentrate on the mind muscle connection .",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderFly.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.RearDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.RearDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Back
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.RearDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
            ),
            coeffOverloading = 7f,
            coeffMusclesInvolved = 4f,
            coeffStrenghtPotential = 3f,
            coeffTechnique = 3f,
            coeffRangeOfMotion = 7f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.PeckDeck,
            )
        ),
        Exercise(
            name = "Standing Dumbbell Front  Raise ",
            description = "This is a very good option to isolate the front delt . Tension is not consistent through out the range of motion",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Shoulders.ordinal,
            movementCategory = ExerciseMovement.ShoulderRaise.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.FrontDelts.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.FrontDelts),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.FrontDelt),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffOverloading = 6F,
            coeffMusclesInvolved = 2f,
            coeffStrenghtPotential = 1f,
            coeffTechnique = 4f,
            coeffRangeOfMotion = 5f,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Dumbbells,
            )
        )
    )
    val tricepsExercises = listOf(
        Exercise(
            name = "Straight-Bar Triceps Push-Down",
            description = "One of the easiest and effective exercise at targeting the outer part of your triceps .The straight bar gives you more stability which will improve your contraction.",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Triceps.ordinal,
            movementCategory = ExerciseMovement.TricepsPushDown.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Triceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Triceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.TricepsLateral,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffRangeOfMotion = 8f,
            coeffTechnique = 3f,
            coeffStrenghtPotential = 3f,
            coeffMusclesInvolved = 2f,
            coeffOverloading = 9f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Pronated),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "EZ-Bar SkullCrushers",
            description = "One of the most effective exercise at targeting the long head of the triceps. The exercise will put your triceps in a stretched position from the start which will help you build huuuuge arms .The Ez-Bar will put your wrist in a safe position",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Triceps.ordinal,
            movementCategory = ExerciseMovement.TricepsExtension.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Triceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Triceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsLateral,
                MuscleGroupsExpanded.TricepsMedial
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffRangeOfMotion = 10f,
            coeffTechnique = 5f,
            coeffStrenghtPotential = 4f,
            coeffMusclesInvolved = 4f,
            coeffOverloading = 10f,
            position = ExercisePosition.Flat.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Pronated),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "Machine Triceps Push-Down",
            description = " This movement is the main function of the triceps.The machine will help you to learn the movement and improve the mind-muscle connection.",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Triceps.ordinal,
            movementCategory = ExerciseMovement.TricepsPushDown.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Triceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Triceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.TricepsLateral,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffRangeOfMotion = 7f,
            coeffTechnique = 4f,
            coeffStrenghtPotential = 2f,
            coeffMusclesInvolved = 2f,
            coeffOverloading = 8f,
            position = ExercisePosition.Seated.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Seat)
        ),
        Exercise(
            name = "Close-Grip Bench Press",
            description = " By shortening the grip , the emphasise will be moved from the chest toward the triceps as the primary mover . The barbell will help you with stability and allow a greater amount of weight to be used",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Triceps.ordinal,
            movementCategory = ExerciseMovement.ChestPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Triceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Triceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Chest,
                MuscleGroupsSimple.FrontDelts
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.TricepsLateral,
                MuscleGroupsExpanded.TricepsLong,
                MuscleGroupsExpanded.TricepsMedial
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.UpperPec,
                MuscleGroupsExpanded.MiddlePec,
                MuscleGroupsExpanded.FrontDelt
            ),
            coeffRangeOfMotion = 8f,
            coeffTechnique = 5f,
            coeffStrenghtPotential = 5f,
            coeffMusclesInvolved = 4f,
            coeffOverloading = 10f,
            position = ExercisePosition.Flat.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.Narrow),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Bench,
                ExerciseEquipment.Barbell
            )
        ),
    )
    val rowExercises = listOf(
        Exercise(
            name = "Barbell Row",
            description = "One of the staple exercises to build a complete and strong posterior chain. Great exercise to develop thickness in the upper back, wide lats and a strong lower back. The barbell gives the chance to move a lot of weight",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.Row.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Biceps,
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.SpinalErectors,
                MuscleGroupsExpanded.Biceps,
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 6f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 6f,
            position = ExercisePosition.BentOver.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Pronated,
                ExerciseGripType.Supinated
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.ShoulderWidth,
                ExerciseGripWidth.ShoulderWidth
            ),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Barbell)
        ),
        Exercise(
            name = "Cable Row",
            description = "Very effective exercise to target the lats and upper back especially if you have lower back pain . The key is to let the weight down slowly to allow the lat to stretch and drive your elbows behind. A wide grip will target more the upper-back while a narrow one will target the lats more. The more you rotate the palm towards you , the more the biceps will be involved. ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.Row.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Biceps,
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.Biceps,
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 6f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 6f,
            position = ExercisePosition.Seated.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Pronated,
                ExerciseGripType.Supinated,
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.ShoulderWidth,
                ExerciseGripWidth.ShoulderWidth
            ),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.CablePulley)
        ),
        Exercise(
            name = "Machine Chest-Supported Row",
            description = "Very similar to any barbell row variant , the only difference is that you will keep your lower back safe from pain . Great exercise to develop thickness in the upper back and wide lats.",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.Row.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Biceps,
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.SpinalErectors,
                MuscleGroupsExpanded.Biceps,
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 6f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 5f,
            coeffRangeOfMotion = 6f,
            position = ExercisePosition.Seated.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Pronated,
                ExerciseGripType.Supinated
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.ShoulderWidth,
                ExerciseGripWidth.ShoulderWidth
            ),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.RowMachine)
        )
    )
    val verticalPullExercises = listOf(
        Exercise(
            name = "Pull-Ups",
            description = "The most popular exercise to build a strong back . The movement involves almost every muscle in back and is not so beginner friendly due to it's strength and technique requirements",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.VerticalPull.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Biceps,
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.SpinalErectors,
                MuscleGroupsExpanded.Biceps,
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 7f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 7f,
            coeffRangeOfMotion = 9f,
            position = ExercisePosition.Hanging.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Pronated,
                ExerciseGripType.Supinated,
                ExerciseGripType.Pronated
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.ShoulderWidth,
                ExerciseGripWidth.ShoulderWidth,
                ExerciseGripWidth.ShoulderWidth
            ),
            isBodyWeight = true,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.PullUpBar)
        ),
        Exercise(
            name = "Assisted Pull-Ups",
            description = "This variant will help you build the strength and confidence to do the conventional Pull-Ups. The movement involves almost every muscle in back",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.VerticalPull.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Biceps,
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.RearDelt,
                MuscleGroupsExpanded.SpinalErectors,
                MuscleGroupsExpanded.Biceps,
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 7f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 7f,
            coeffRangeOfMotion = 9f,
            position = ExercisePosition.Hanging.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Pronated,
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.Wide,

                ),
            isBodyWeight = true,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.PullUpMachine)
        ),
        Exercise(
            name = "Chin Ups",
            description = "A very popular exercise that is easier than a pull-up which makes from it a great alternative with the caveat of being a bicep builder",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.VerticalPull.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Back,
                MuscleGroupsSimple.Biceps
            ),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Forearms
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Lats
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Forearms
            ),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 6f,
            coeffStrenghtPotential = 4f,
            coeffTechnique = 7f,
            coeffRangeOfMotion = 9f,
            position = ExercisePosition.Hanging.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Supinated,
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.ShoulderWidth,

                ),
            isBodyWeight = true,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.PullUpBar)
        ),
        Exercise(
            name = "Deadlift",
            description = "Is the best exercise to build a very powerful posterior chain . Almost every muscle in the back , legs and arms are involved in this movement which makes a great option to improve your overall performance ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Back.ordinal,
            movementCategory = ExerciseMovement.Hinge.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Back.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Back,
                MuscleGroupsSimple.Legs
            ),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(
                MuscleGroupsSimple.Forearms,
                MuscleGroupsSimple.Abs
            ),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Traps,
                MuscleGroupsExpanded.Rhomboids,
                MuscleGroupsExpanded.TeresAndInfra,
                MuscleGroupsExpanded.SpinalErectors,
                MuscleGroupsExpanded.Glutes,
                MuscleGroupsExpanded.Hamstrings,
                MuscleGroupsExpanded.Quads
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Forearms,
                MuscleGroupsExpanded.Abs
            ),
            coeffOverloading = 10f,
            coeffMusclesInvolved = 8f,
            coeffStrenghtPotential = 7f,
            coeffTechnique = 9f,
            coeffRangeOfMotion = 9f,
            position = ExercisePosition.Upright.ordinal,
            gripTypeList = ExerciseGripType.buildString(
                ExerciseGripType.Alternate,
                ExerciseGripType.Alternate,
            ),
            gripWidthList = ExerciseGripWidth.buildString(
                ExerciseGripWidth.KneeWidth,
                ExerciseGripWidth.Narrow,
            ),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Barbell)
        ),
    )
    val bicepsCurls = listOf(
        Exercise(
            name = "Standing Dumbbell Bicep Curl" ,
            description = "The most popular exercise to grow your biceps. The key are not to swing the weight , use a lighter weight and squeze at the top" ,
            type = ISOLATION,
            movementCategory = ExerciseMovement.BicepsCurl.ordinal,
            muscleCategory = MuscleCategory.Biceps.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Biceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Biceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.Biceps),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffOverloading = 6f,
            coeffMusclesInvolved = 4f,
            coeffStrenghtPotential = 4f,
            coeffRangeOfMotion = 8f,
            coeffTechnique = 7f ,
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.Normal),
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Dumbbells)
        ),
        Exercise(
            name = "Standing Ez-Bar Bicep Curl" ,
            description = "The Ez-Bar will put your wrist in a safe position and allow you to curl more weight. The key are not to swing the weight , use a lighter weight and squeze at the top" ,
            type = ISOLATION,
            movementCategory = ExerciseMovement.BicepsCurl.ordinal,
            muscleCategory = MuscleCategory.Biceps.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Biceps.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Biceps),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.Biceps),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffOverloading = 8f,
            coeffMusclesInvolved = 4f,
            coeffStrenghtPotential = 4f,
            coeffRangeOfMotion = 8f,
            coeffTechnique = 7f ,
            position = ExercisePosition.Upright.ordinal,
            isBodyWeight = false,
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.Normal),
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.EzBar)
        )
    )
    val legPressExercises = listOf(
        Exercise(
            name = "Barbell Squat",
            description = "If you want to build a complete physique the squat is a must in any program . It maybe intimidating in the beginning but through persistance you will be gratefull you strated squatting . If you want to target the glutes more widen the stance but if you want to target the quads , a shoulder-width stance is recommended",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.LegPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Quads,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Hamstrings,
                MuscleGroupsExpanded.LowerBack
            ),
            coeffTechnique = 7f,
            coeffRangeOfMotion = 8f,
            coeffStrenghtPotential = 10f,
            coeffMusclesInvolved = 7f,
            coeffOverloading = 10f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.Barbell,
                ExerciseEquipment.SquatRack
            )
        ),
        Exercise(
            name = "Smith-Machine Bulgarian Squat",
            description = "This exercise allows training each leg through the squat movement pattern which may decrease imbalances between the left and right foot. The setup and the balanced required are a little bit tricky to figure out which makes it not so popular among beginner lifters .",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.LegPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Quads,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Hamstrings,
                MuscleGroupsExpanded.LowerBack
            ),
            coeffTechnique = 9f,
            coeffRangeOfMotion = 8f,
            coeffStrenghtPotential = 7f,
            coeffMusclesInvolved = 7f,
            coeffOverloading = 8f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(
                ExerciseEquipment.SmithMachine,
                ExerciseEquipment.Bench
            )
        ),

        Exercise(
            name = "Hack-Squat",
            description = "A very effective substitute for the squat . Your lower back will be kept safe while you can concentrate on the movement",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.LegPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Quads,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffTechnique = 5f,
            coeffRangeOfMotion = 10f,
            coeffStrenghtPotential = 6f,
            coeffMusclesInvolved = 7f,
            coeffOverloading = 9f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.LegMachine)
        ),
        Exercise(
            name = "Leg-Press",
            description = "A very good substitute for the squat to build pressing power . While you keep your butt on the seat and dont't round your lower back it will be kept safe ",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.LegPress.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Quads,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffTechnique = 5f,
            coeffRangeOfMotion = 10f,
            coeffStrenghtPotential = 6f,
            coeffMusclesInvolved = 7f,
            coeffOverloading = 9f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.LegMachine)
        )
    )
    val legExtentionExercises = listOf(
        Exercise(
            name = "Dumbbell Romanian Deadlift",
            description = "The feeling of burn you are going to feel in your hamstrings and glutes while you do this exercise is incomparable with other exercises . One limiting factor will be your grip strength , which will make it difficult to hit high rep ranges .",
            type = COMPOUND,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.Hinge.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Hamstrings,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.LowerBack),
            coeffTechnique = 9f,
            coeffRangeOfMotion = 10f,
            coeffStrenghtPotential = 6f,
            coeffMusclesInvolved = 7f,
            coeffOverloading = 8f,
            position = ExercisePosition.Upright.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Dumbbells)
        ),
        Exercise(
            name = " Assisted Leg Extension",
            description = "The machine will help you dial-down the movement and improve the mind muscle connection",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Legs.ordinal,
            movementCategory = ExerciseMovement.LegExtension.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Legs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Legs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Back),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(
                MuscleGroupsExpanded.Hamstrings,
                MuscleGroupsExpanded.Glutes
            ),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffTechnique = 5f,
            coeffRangeOfMotion = 9f,
            coeffStrenghtPotential = 5f,
            coeffMusclesInvolved = 3f,
            coeffOverloading = 8f,
            position = ExercisePosition.Seated.ordinal,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.ShoulderWidth),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            isBodyWeight = false,
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.LegMachine)
        )
    )
    val abCrunchExercises = listOf(
        Exercise(
            name = "Decline Crunch",
            description = "This exercise will target the upper abdominal . Steeper the bench  the more difficult the exercise will become",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Abs.ordinal,
            movementCategory = ExerciseMovement.AbCrunch.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Abs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Abs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.Abs),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffTechnique = 6f,
            coeffRangeOfMotion = 8f,
            coeffStrenghtPotential = 5f,
            coeffMusclesInvolved = 5f,
            coeffOverloading = 5f,
            isBodyWeight = true,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.Normal),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.Bench),
            position = ExercisePosition.Decline.ordinal
        )
    )
    val abLegRaiseExercises = listOf(
        Exercise(
            name = "Hanging Leg Raise",
            description = "One of the most difficult exercise to perform because it requires a lot of core strength to perform",
            type = ISOLATION,
            muscleCategory = MuscleCategory.Abs.ordinal,
            movementCategory = ExerciseMovement.AbLegRaise.ordinal,
            primaryMuscleTag = MuscleGroupsSimple.Abs.ordinal,
            primaryMusclesMinimal = MuscleGroupsSimple.buildString(MuscleGroupsSimple.Abs),
            secondaryMusclesMinimal = MuscleGroupsSimple.buildString(),
            primaryMusclesExpanded = MuscleGroupsExpanded.buildString(MuscleGroupsExpanded.Abs),
            secondaryMusclesExpanded = MuscleGroupsExpanded.buildString(),
            coeffTechnique = 6f,
            coeffRangeOfMotion = 8f,
            coeffStrenghtPotential = 5f,
            coeffMusclesInvolved = 5f,
            coeffOverloading = 5f,
            isBodyWeight = true,
            gripWidthList = ExerciseGripWidth.buildString(ExerciseGripWidth.Normal),
            gripTypeList = ExerciseGripType.buildString(ExerciseGripType.Normal),
            equipment = ExerciseEquipment.buildString(ExerciseEquipment.PullUpBar),
            position = ExercisePosition.Hanging.ordinal
        )
    )
}