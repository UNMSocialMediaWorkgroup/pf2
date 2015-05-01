package edu.unm.carc.socialmedia.party.image

import java.util

import edu.unm.carc.socialmedia.party.drivers.ScalaFXDriver
import edu.unm.carc.socialmedia.party.physics.Particle
import edu.unm.carc.socialmedia.party.util.ScalaFXUtil

import scala.annotation.tailrec
import scala.collection.parallel.mutable.ParArray
import scala.util.Random
import scalafx.scene.image.{PixelFormat, WritableImage}

/**
 * Created by sixstring982 on 4/22/15.
 */
class PartyImage
  extends WritableImage(ScalaFXDriver.WIDTH, ScalaFXDriver.HEIGHT) {

  val impurityQueue = new util.ArrayDeque[Int]

  def addImpurities(): Unit = {
    impurityQueue.add(PartyImage.rand.nextInt(50))
  }

  def doFrame(buffer: HistoryBuffer, particles: ParArray[Particle]): Unit = {
    render(buffer)
    queueFrame(buffer.flip(particles),
      addMissingParticles(removeOldParticles(particles.map(_.gravitate()))))
  }

  private def render(buffer: HistoryBuffer): Unit = {
    pixelWrit.setPixels(0, 0, ScalaFXDriver.WIDTH, ScalaFXDriver.HEIGHT,
                        PixelFormat.getIntArgbInstance,
                        buffer.asRenderable(), 0, ScalaFXDriver.WIDTH)
  }

  private def queueFrame(buffer: HistoryBuffer,
                         particles: ParArray[Particle]): Unit = {
    ScalaFXUtil.runLater(() ⇒ doFrame(buffer, particles))
  }

  private def removeOldParticles(particles: ParArray[Particle]):
    ParArray[Particle] = {
    particles.filter(_.pos.x < ScalaFXDriver.WIDTH)
             .filter(a ⇒ a.pos.y < ScalaFXDriver.HEIGHT)

    particles.map {
      p ⇒ p.pos.y match {
        case y if y > ScalaFXDriver.HEIGHT && p.gonnaBeRed ⇒ p.bounce()
        case y ⇒ p
      }
    }
  }

  private def addMissingParticles(particles: ParArray[Particle]):
    ParArray[Particle] = {
    @tailrec
    def addMultipleParticles(left: Int, particles: ParArray[Particle]):
      ParArray[Particle] = left match {
        case 0 ⇒ particles
        case x ⇒ addMultipleParticles(x - 1,
          particles :+ Particle.newRandomParticle())
        /* This should only be in the bouncing branch */
      }

    @tailrec
    def addRedParticles(left: Int, particles: ParArray[Particle]):
      ParArray[Particle] = left match {
        case 0 ⇒ particles
        case x ⇒ addRedParticles(x - 1,
          particles :+ Particle.newRedParticle())
      }


    addMultipleParticles(
      Math.min(PartyImage.rand.nextInt(10) + 1,
               PartyImage.PARTICLE_COUNT - particles.size match {
                 case x if x <= 0 ⇒ 0
                 case x ⇒ x
               }),
      addRedParticles(impurityQueue.size() match {
        case 0 ⇒ 0
        case x ⇒ impurityQueue.removeFirst()
      }, particles))
  }

  queueFrame(HistoryBuffer.initial, ParArray[Particle]())
}

object PartyImage {
  val PARTICLE_COUNT = 7500

  val rand: Random = new Random
}
