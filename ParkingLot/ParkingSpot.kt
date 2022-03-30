data class ParkingSpot(
    var numberSpot: Int,
    var numberCarInToSpot: String,
    var colorCar: String,
    var status: Boolean
) {
    override fun toString(): String {
        return "ParkingSpot(numberSpot=$numberSpot, numberCarInToSpot='$numberCarInToSpot', " +
                "colorCar='$colorCar', status='$status')"
    }

    operator fun invoke(s: String, s1: String, b: Boolean) {
        numberCarInToSpot = s
        colorCar = s1
        status = b
    }
}
