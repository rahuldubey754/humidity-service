package service

import model.LeadersDailySummary

class PrinterService {

  def print(dailySummary: LeadersDailySummary): Unit = {

    println(s"Num of processed files: ${dailySummary.noOfLeaders}")
    println(s"Num of processed measurements: ${dailySummary.processedMeasurements}")
    println(s"Num of failed measurements: ${dailySummary.failedMeasurements}")
    println(s"Sensors with highest avg humidity:")
    println(s"sensor-id,min,avg,max")
    println(s"${dailySummary.allSensorsAverageReadings.map(s => s"${s.id},${s.min},${s.avg},${s.max}").mkString("\n")}")

  }

}
