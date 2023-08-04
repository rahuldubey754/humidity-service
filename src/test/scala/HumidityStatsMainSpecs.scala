import java.io.File

import model.{CorrectReading, FaultyReading, LeaderInput, LeadersDailySummary, SensorDailySummary}
import org.scalatest.funspec.AnyFunSpec
import service.{DailySummaryService, FileReaderService, LeaderService, PrinterService}


class HumidityStatsMainSpecs extends AnyFunSpec {

  lazy val fileReaderService    = new FileReaderService
  lazy val leaderService        = new LeaderService
  lazy val dailySummaryService  = new DailySummaryService
  lazy val printerService       = new PrinterService

  describe("Humidity Service Module") {

    describe("File Reader Service") {
      it("should read files correctly ") {

        //given
        val expectedFileNames = List("leader-2.csv", "leader-1.csv")

        //when
        val actualFilesList: List[File] = fileReaderService.getAllFilesFrom("src/test/scala/resources/")
        val actualFileNames: List[String] = actualFilesList.map(_.getName)

        //then
        assert(expectedFileNames.size == actualFilesList.size)
        assert(expectedFileNames == actualFileNames)

      }

      it("should return empty list when input path is wrong") {

        //given
        val path = "some/random/path/"

        //when
        val filesList = fileReaderService.getAllFilesFrom(path)

        //then
        assert(filesList == Nil)
      }
    }

    describe("Leader Service") {
      it("should return Leader Input List correctly ") {

        //given
        val expectedLeaderInputs = List(LeaderInput("leader-2.csv",List(CorrectReading("s2",80), FaultyReading("s3",0), CorrectReading("s2",78), CorrectReading("s1",98))), LeaderInput("leader-1.csv",List(CorrectReading("s1",10), CorrectReading("s2",88), FaultyReading("s1",0))))

        //when
        val files: List[File] = fileReaderService.getAllFilesFrom("src/test/scala/resources/")
        val actualLeaderInputs: List[LeaderInput] = leaderService.getLeaderInputsFrom(files)

        //then
        assert(expectedLeaderInputs.size == actualLeaderInputs.size)
        assert(expectedLeaderInputs == actualLeaderInputs)

      }

      it("should return empty list when input file list is empty") {

        //given
        //Input list is empty

        //when
        val leaderInputs = leaderService.getLeaderInputsFrom(Nil)

        //then
        assert(leaderInputs == Nil)
      }
    }

    describe("Daily Summary Service") {
      it("should return Daily summary correctly ") {

        val expectedLeaderInputs = List(LeaderInput("leader-2.csv",List(CorrectReading("s2",80), FaultyReading("s3",0), CorrectReading("s2",78), CorrectReading("s1",98))), LeaderInput("leader-1.csv",List(CorrectReading("s1",10), CorrectReading("s2",88), FaultyReading("s1",0))))

        //given
        val files: List[File] = fileReaderService.getAllFilesFrom("src/test/scala/resources/")
        val leaderInputs: List[LeaderInput] = leaderService.getLeaderInputsFrom(files)
        val expectedDailySummary = LeadersDailySummary(2,7,2,List(SensorDailySummary("s2","78","82","88",3,0), SensorDailySummary("s1","10","54","98",2,1), SensorDailySummary("s3","NaN","NaN","NaN",0,1)))

        //when
        val actualDailySummary = dailySummaryService.getDailySummaryFrom(leaderInputs)

        //then
        assert(expectedDailySummary == actualDailySummary)

      }

    }
  }
}