package service

import java.io.File

import scala.io.Source
import model.{LeaderInput, SensorSingleReading}


class LeaderService {

  def getLeaderInputsFrom(files: List[File]): List[LeaderInput] =
    files.map{
      leaderFile =>
        getLeaderInputFrom(leaderFile.getName, Source.fromFile(leaderFile.getAbsolutePath).getLines.toList.filterNot(_ equals "sensor-id,humidity"))
    }

  private def getLeaderInputFrom(name: String, sensorsData: List[String]): LeaderInput = {

    LeaderInput(name, sensorsData.foldLeft(List.empty[SensorSingleReading]){
      (sensorsDataList, oneSensorData) => sensorsDataList :+ {
        val idAndHumidityData = oneSensorData.split(",")
        SensorSingleReading(idAndHumidityData(0), idAndHumidityData(1))
      }
    })

  }

}
