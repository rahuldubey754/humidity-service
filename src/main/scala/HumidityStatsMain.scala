import java.io.File

import model.{LeaderInput, LeadersDailySummary}
import service.{DailySummaryService, FileReaderService, LeaderService, PrinterService}

object HumidityStatsMain extends App{

  lazy val fileReaderService    = new FileReaderService
  lazy val leaderService        = new LeaderService
  lazy val dailySummaryService  = new DailySummaryService
  lazy val printerService       = new PrinterService


  //Please specify the input file path
  val inputPath = "/Users/rdq5030/AdisFE/repo/humidity-matrics-service/src/resources/"

  val leaderFiles: List[File]          = fileReaderService getAllFilesFrom inputPath
  val inputLeaders: List[LeaderInput]  = leaderService getLeaderInputsFrom leaderFiles
  val dailySummary: LeadersDailySummary   = dailySummaryService getDailySummaryFrom inputLeaders

    printerService print dailySummary

}
