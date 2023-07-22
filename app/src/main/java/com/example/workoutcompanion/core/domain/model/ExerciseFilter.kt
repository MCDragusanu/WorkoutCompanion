package com.example.workoutcompanion.core.domain.model;

 enum class ExerciseFilter(
    val uid:Int,
    val filterName:String,
    val description:String,
    val category:String,

    val weight: Float
                          ) {


     WristPain (
        weight = -0.4f,
        uid = 0,
        filterName = "Wrist Pain",
        description = "These exercises put considerable amount of pressure in the wrists ",
        category = "Exercise",

    ),

     KneePain(
        weight = -0.4f,
        uid = 1,
        filterName = "Knee Pain",
        description = "These exercises put considerable amount of pressure in the wrists",
        category = "Exercise",

    ),

     LowerBackPain (
        weight = -0.4f,
        uid = 2,
        filterName = "Lower Back pain",
        description = "These exercises put considerable amount of pressure in the wrists",
        category = "Exercise",

    ),

     ShoulderPain(
        weight = -0.4f,
        uid = 3,
        filterName = "Shoulder Pain",
        description = "These exercises put considerable amount of pressure in the wrists",
        category = "Exercise",

    ),

     PowerliftingFocus (
        weight = 0.50f,
        uid = 4,
        filterName = "Powerlifting Focus",
        description = "I want to focus on powerlifting movements",
        category = "Exercise" + "/" + "Movement",

    ),

     FreeWeightFocus (
        weight = 0.2f,
        uid = 5,
        filterName = "Free Weight Focus",
        description = "I want to focus on free weights exercises",
        category = "Exercise",

    ),

    MachineFocus(
        weight = 0.3f,
        uid = 6,
        filterName = "Machine Focus",
        description = "I want to focus on machine assisted exercises",
        category = "Exercise",

    ),

     CompoundFocus(
        weight = 0.25f,
        uid = 7,
        filterName = "Compound Focus",
        description = "I want to focus on compound exercises",
        category = "Movement",

    ),

     CalisthenicsFocus(
        weight = 0.35f,
        uid = 8,
        filterName = "Calisthenics Focus",
        description = "I want to focus on Calisthenics Movement",
        category = "Movement" + "/" + "Exercise",

    ),

     Isolation(
        weight = 0.45f,
        uid = 9,
        filterName = "Isloation Focus",
        description = "I want to focus on isolation Movement",
        category = "Movement",

    );

}