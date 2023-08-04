package service

import model.{CorrectReading, FaultyReading, LeaderInput, LeadersDailySummary, SensorDailySummary, SensorSingleReading}

class DailySummaryService {

  def getDailySummaryFrom(leaderInputs: List[LeaderInput]): LeadersDailySummary = {

    val noOfLeaders = leaderInputs.length
    val allMeasurements: List[SensorSingleReading] = leaderInputs.map(_.sensorsData).flatten
    val failedMeasurements: List[SensorSingleReading] =allMeasurements.filter(_.isInstanceOf[FaultyReading])


    val sensorDailySummariesRaw = allMeasurements.groupBy(_.id).map(s => getSensorDailySummary(s._1, s._2)).toList

    val sensorDailySummariesSorted = (sensorDailySummariesRaw.filterNot(x => x.avg.equals("NaN"))).sortWith(_.avg > _.avg) ++
                                      (sensorDailySummariesRaw.filter(x => x.avg.equals("NaN")))

    LeadersDailySummary(noOfLeaders, allMeasurements.length, failedMeasurements.length, sensorDailySummariesSorted)

  }

  private def getSensorDailySummary(id: String, allReadings: List[SensorSingleReading]): SensorDailySummary = {

    val processed: List[SensorSingleReading] = allReadings.filter(_.isInstanceOf[CorrectReading])
    val failed: List[SensorSingleReading]    = allReadings.filter(_.isInstanceOf[FaultyReading])

    val sumOfAllReadings: Int = allReadings.map(_.humidity).sum


    val average: String = if(processed.length>0) (sumOfAllReadings/processed.length).toString else "NaN"
    val min: String = if(processed.length>0) processed.map(_.humidity).min.toString else "NaN"
    val max: String = if(processed.length>0) processed.map(_.humidity).max.toString else "NaN"

    SensorDailySummary(id, min, average, max, processed.length, failed.length)

  }

}
