package model

case class LeadersDailySummary(noOfLeaders: Int, processedMeasurements: Int, failedMeasurements: Int, allSensorsAverageReadings: List[SensorDailySummary])

