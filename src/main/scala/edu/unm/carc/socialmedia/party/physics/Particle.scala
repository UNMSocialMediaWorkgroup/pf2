package edu.unm.carc.socialmedia.party.physics

import edu.unm.carc.socialmedia.party.drivers.ScalaFXDriver
import edu.unm.carc.socialmedia.party.image.PartyImage

/**
 * Created by sixstring982 on 4/22/15.
 */
case class Particle(pos: Vector2,
                    vel: Vector2,
                    acc: Vector2,
                    age: Int,
                    green: Int,
                    red: Boolean) {
  def gravitate(): Particle = {
    new Particle(
      pos = {
        val newpos = pos + vel
        newpos.distance(pos) match {
          case x if x > 1.0 ⇒ pos + vel.normalize()
          case _ ⇒ newpos
        }
      },
      vel = vel + acc,
      acc = acc + Vector2.gravity,
      age = age + 1,
      green = green,
      red = red
    )
  }
}

object Particle {
  def newRandomParticle(): Particle = {
    new Particle(
      pos = new Vector2(PartyImage.rand.nextInt(ScalaFXDriver.WIDTH), 0),
      vel = new Vector2(PartyImage.rand.nextDouble() * 0.8 + 0.125, 0),
      acc = Vector2.empty,
      age = 0,
      green = PartyImage.rand.nextInt(100) + 50,
      red = false
    )
  }

  def newRedParticle(): Particle = {
    new Particle(
      pos = new Vector2(PartyImage.rand.nextInt(ScalaFXDriver.WIDTH), 0),
      vel = new Vector2(PartyImage.rand.nextDouble() * 0.8 + 0.125, 0),
      acc = Vector2.empty,
      age = 0,
      green = PartyImage.rand.nextInt(100) + 50,
      red = true
    )
  }
}