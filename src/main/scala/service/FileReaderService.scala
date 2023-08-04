package service

import java.io.File

import scala.util.{Failure, Success, Try}


class FileReaderService {

  def getAllFilesFrom(path: String): List[File] = {
    Try {
      new File(path).listFiles
    }
    match {
      case Success(null) => Nil
      case Success(value) => value.toList
      case Failure(_) => Nil
    }
  }

}
