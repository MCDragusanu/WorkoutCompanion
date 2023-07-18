package com.example.workoutcompanion.exercise_database.model



/*clasa care contine acele trasaturile generale ale exercitiului*/
/*familia exercitiului va contine informatii relevante categoriei de exercitii*/
/*exercitiile vor fi o implementare a familiei*/
enum class Movement(
    val description:String,
    val type:Int,//Isolation or Compound
    val primaryMuscleGroups:List<Pair<MuscleGroup, Double>>,
    val secondaryMuscleGroups:List<Pair<MuscleGroup, Double>>,
    val instructionList:List<ExerciseInstruction> = emptyList(),
    val movementUid :Int
                    ) {


   ChestPress(

        description = "The chest press is a fundamental and versatile exercise movement that primarily targets the muscles of the chest, as well as the shoulders and triceps",
        type = 0,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Chest, 1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Triceps, 0.5), Pair(MuscleGroup.FrontDelts, 0.5)),
        movementUid = 0,
        instructionList = emptyList()
    ),

    ChestDips(

        description = "The chest dip can be described as a vertical pushing movement pattern. It involves the coordinated action of multiple muscle groups to lower and raise the body using the arms as the primary lever.",
        type = 0,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Chest, 1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Triceps, 0.4), Pair(MuscleGroup.FrontDelts, 0.3)),
        movementUid = 1,
        instructionList = emptyList()
    ),

   ChestFly(

        description = "The chest fly is a movement that targets the muscles of the chest, particularly the pectoralis major and pectoralis minor. It involves a horizontal adduction movement pattern, simulating the motion of hugging",
        type = 1,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Chest, 1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.FrontDelts, 0.25)),
        movementUid = 2,
        instructionList = emptyList(),
    ),

      ShoulderPress(

        description = "The vertical press is a common exercise movement that primarily targets the muscles of the shoulders, particularly the deltoids, while also engaging the triceps and upper chest. It involves a pressing motion performed in a vertical plane, typically overhead.",
        type = 0,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.FrontDelts,1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.SideDelts, 0.5), Pair(MuscleGroup.Triceps, 0.5)),
        movementUid = 3,
        instructionList = emptyList()
    ),

    ShoulderLateralFly(

        description = "The lateral raise is movement that targets the muscles of the shoulders, particularly the lateral deltoids. It involves a lateral abduction movement pattern, where the arms are raised laterally away from the body.",
        type = 1,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.SideDelts, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 4,
        instructionList = emptyList()
    ),

    ShoulderRearFly(

        description = "The Rear Delt Fly is an isolation exercise that primarily targets the posterior deltoids, the muscles located at the back of the shoulders. It involves a posterior or rearward movement pattern, where the arms are raised away from the body in the horizontal plane.",
        type = 1,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.RearDelts, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 5,
        instructionList = emptyList()
    ),

    ShoulderFrontFly(

        description = "This movement primarily targets the anterior deltoids, the muscles located at the front of the shoulders. It involves an anterior or forward movement pattern, where the arms are raised in front of the body.",
        type = 1,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.FrontDelts, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 6,
        instructionList = emptyList()
    ),

    Row(
        description = "The row is a compound movement that targets the muscles of the back, particularly the lats, rhomboids, and upper back. It involves a pulling movement pattern, where you pull a weight towards your body while maintaining a stable and controlled position.",
        type = 0,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.UpperBack,1.0),Pair(MuscleGroup.Lats, 0.5)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Biceps, 0.5)),
        movementUid = 7,
        instructionList = emptyList()
    ),

     VerticalPull(

        description = "The vertical pull is a compound movement that primarily targets the muscles of the back, including the latissimus dorsi, rhomboids, and biceps. It involves a pulling movement pattern performed in a vertical plane, typically involving pulling yourself upward towards a fixed bar or handle.",
        type = 0,
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Lats, 1.0) , Pair(MuscleGroup.UpperBack, 0.5)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Biceps, 0.5)),
        movementUid = 8,
        instructionList = emptyList()
    ),

     Deadlift(

        type = 0,
        description = "The deadlift is a compound movement that involves lifting a weight from the ground using a hip-hinge movement pattern. It is one of the most fundamental and effective exercises for developing overall strength and power.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Glutes, 1.0), Pair(MuscleGroup.Hamstrings, 1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Quads, 0.5)),
        movementUid = 9,
        instructionList = emptyList()
    ),

     BicepsCurl(

        type = 1,
        description = "The biceps curl is an isolation movement that involves flexing the elbow joint to lift a weight towards the shoulder. It is a common exercise for targeting the muscles in the upper arm.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Biceps, 1.0)),
        secondaryMuscleGroups = emptyList(),
        movementUid = 10,
        instructionList = emptyList()
    ),

    TricepsExtension(

        type = 1,
        description = "The triceps extension is an isolation movement that targets the muscles at the back of the upper arm, specifically the triceps brachii. It involves extending the elbow joint to work the triceps effectively.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Triceps, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 11
    ),

    TricepsPushdown(

        type = 1,
        description = "The triceps push down is an isolation movement that targets the triceps muscles, located at the back of the upper arm. It involves extending the elbow joint while keeping the upper arms stationary, effectively working the triceps.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Triceps, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 12,
        instructionList = emptyList()
    ),

  Squat(
        type = 0,
        description = "The squat is a compound movement that primarily targets the muscles of the lower body, including the quadriceps, hamstrings, and glutes. It is a fundamental movement pattern that involves bending at the knees and hips to lower the body while maintaining proper form and balance.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Quads, 1.0), Pair(MuscleGroup.Glutes, 1.0),),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Hamstrings, 0.5)),
        movementUid = 20,
        instructionList = emptyList()
    ),
   GlutesKickBacks(
        type = 1,
        description = "The Glute Kickback primarily targets the gluteus",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Glutes, 1.0),),
        secondaryMuscleGroups = listOf(),
        movementUid = 21,
        instructionList = emptyList()),

   HipThrusts(
        type = 1,
        description = "By driving through your heels, you lift your hips off the ground and extend your hips until your body forms a straight line. It targets the glutes and is particularly effective for building strength and size in the glute muscles.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Glutes, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 13,
        instructionList = emptyList()),

   Lunge(

        type = 1,
        description = "The lunge is a compound movement that targets the muscles of the lower body, including the quadriceps, hamstrings, glutes, and calves. It is a versatile movement that can be performed in various directions and with or without weights.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Quads, 1.0)),
        secondaryMuscleGroups = listOf(Pair(MuscleGroup.Glutes, 0.25)),
        movementUid = 14,
        instructionList = emptyList()
    ),

     LegCurl(

        type = 1,
        description = "The leg curl is an isolation movement that primarily targets the hamstrings, the muscles located on the back of your thigh. It involves flexing the knee joint against resistance to activate and strengthen the hamstrings.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Hamstrings, 1.0) , Pair(MuscleGroup.Glutes, 0.5)),
        secondaryMuscleGroups = listOf(),
        movementUid = 15,
        instructionList = emptyList()
    ),

     LegExtension(

        type = 1,
        description = "Leg extensions is a movement that primarily targets the muscles of the quadriceps, located on the front of the thigh. It involves extending the knee joint against resistance to activate and strengthen the quadriceps.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Quads, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 16,
        instructionList = emptyList()
    ),

     CalvesRaise(

        type = 1,
        description = "Calf raises is movement that target the muscles of the calf, specifically the gastrocnemius and soleus muscles. They help strengthen and develop the lower leg, improving ankle stability and overall lower body strength.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Calves, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 17,
        instructionList = emptyList()
    ),

    LegRaise(

        type = 1,
        description = "Leg raises are a popular exercise that primarily target the muscles of the abdominal region, specifically the lower abdominals. They involve lifting your legs while lying on your back to engage and strengthen the core muscles.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Abs, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 18,
        instructionList = emptyList()
    ),

    Crunch(
        type = 1,
        description = "Crunches are a classic exercise that primarily target the muscles of the abdominal region, particularly the rectus abdominis. They involve a partial range of motion where you lift your upper body off the ground, engaging the abdominal muscles to strengthen the core.",
        primaryMuscleGroups = listOf(Pair(MuscleGroup.Abs, 1.0)),
        secondaryMuscleGroups = listOf(),
        movementUid = 19,
        instructionList = emptyList()
    )

}