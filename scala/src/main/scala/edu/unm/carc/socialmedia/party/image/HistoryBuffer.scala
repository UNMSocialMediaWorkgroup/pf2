package edu.unm.carc.socialmedia.party.image

import edu.unm.carc.socialmedia.party.drivers.ScalaFXDriver
import edu.unm.carc.socialmedia.party.physics.Particle

import scala.collection.immutable.HashMap
import scala.collection.parallel.immutable.ParVector
import scala.collection.parallel.mutable.ParArray

/**
 * Created by sixstring982 on 4/23/15.
 */
class HistoryBuffer(val buffer: ParArray[Int]) {

  private val FADE_AMT = 2

  def flip(particles: ParArray[Particle]): HistoryBuffer = {
    new HistoryBuffer({
      val plocs: HashMap[(Int, Int), (Int, Boolean, Boolean)] =
        particles.foldLeft(HashMap[(Int, Int), (Int, Boolean, Boolean)]()) {
          (map: HashMap[(Int, Int), (Int, Boolean, Boolean)],
           next: Particle) ⇒ {
            map + ((next.pos.x.toInt, next.pos.y.toInt) →
                   (next.green, next.red, next.gonnaBeRed))
          }
        }

      ParArray.tabulate(buffer.size) {
        (i: Int) ⇒ {
          val x = i % ScalaFXDriver.WIDTH
          val y = i / ScalaFXDriver.WIDTH
          plocs.get((x, y)) match {
            case Some(a) ⇒ a._2 match {
              case true ⇒ 0xff0000 | (a._1 << 8)
              case false ⇒ 0xff | (a._1 << 8) | (a._3 match {
                case true ⇒ 0xaf0000
                case false ⇒ 0
              })
            }
            case None ⇒
              val blue = (buffer(i) & 0xff) - FADE_AMT match {
                case a if a < 0 ⇒ 0
                case b ⇒ b
              }
              val green = ((buffer(i) >> 8) & 0xff) - FADE_AMT match {
                case a if a < 0 ⇒ 0
                case b ⇒ b
              }
              val red = ((buffer(i) >> 16) & 0xff) - FADE_AMT match {
                case a if a < 0 ⇒ 0
                case b ⇒ b
              }

              blue | (green << 8) | (red << 16)
          }
        }
      }
    })
  }

  def asRenderable(): Array[Int] = {
    buffer.map(_ | 0xff000000).toArray
  }
}

object HistoryBuffer {
  val initial: HistoryBuffer = new HistoryBuffer(
    buffer = Array.tabulate(ScalaFXDriver.HEIGHT, ScalaFXDriver.WIDTH) {
      (x: Int, y: Int) ⇒ 0
    }.flatten.par
  )
}