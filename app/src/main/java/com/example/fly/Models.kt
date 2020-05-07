package com.example.fly

sealed class Models {

    abstract val degreesPerSecond: Float
    abstract val radius: Float
    abstract val height: Float
    abstract val rotationDegrees: Float

    object Bee : Models() {
        override val degreesPerSecond: Float
            get() = 20f
        override val radius: Float
            get() = 2f
        override val height: Float
            get() = 0.7f
        override val rotationDegrees: Float
            get() = 180f
    }
    object Jet : Models() {
        override val degreesPerSecond: Float
            get() = 20f
        override val radius: Float
            get() = 2f
        override val height: Float
            get() = 0.7f
        override val rotationDegrees: Float
            get() = 180f
    }


}



