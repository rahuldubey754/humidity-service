package model

sealed trait SensorSingleReading{
  def id: String
  def humidity: Int
}

case class CorrectReading(id: String, humidity: Int) extends SensorSingleReading
case class FaultyReading(id: String, humidity: Int=0) extends SensorSingleReading

object SensorSingleReading{

  def apply(id: String, humidity: String): SensorSingleReading =

    if(humidity.matches("\\d*"))
      CorrectReading(id, humidity.toInt)
    else
      FaultyReading(id)
}


