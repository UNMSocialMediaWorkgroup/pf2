package edu.unm.carc.socialmedia.party.util

import scalafx.application.Platform

/**
 * Created by sixstring982 on 4/22/15.
 */
object ScalaFXUtil {
  def runLater(method: () ⇒ Unit): Unit = {
    Platform.runLater(new Runnable() {
      override def run(): Unit = method()
    })
  }
}
