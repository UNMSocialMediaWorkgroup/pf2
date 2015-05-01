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
                    red: Boolean,
                    gonnaBeRed: Boolean) {
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
      acc = acc,
      age = age + 1,
      green = green,
      red = red,
      gonnaBeRed = gonnaBeRed
    )
  }

  def bounce(): Particle = {
    new Particle(
      pos = pos,
      vel = new Vector2(vel.x, vel.y * -0.75 * PartyImage.rand.nextDouble()),
      acc = acc,
      age = age,
      green = green,
      red = gonnaBeRed,
      gonnaBeRed = gonnaBeRed
    )
  }

  def bounceAndRedden(): Particle = {
    new Particle(
      pos = pos,
      vel = new Vector2(vel.x, vel.y * -0.75 * PartyImage.rand.nextDouble()),
      acc = acc,
      age = age,
      green = green,
      red = true,
      gonnaBeRed = gonnaBeRed
    )
  }
}

object Particle {
  def newRandomParticle(): Particle = {
    new Particle(
      pos = new Vector2(PartyImage.rand.nextInt(ScalaFXDriver.WIDTH), 0),
      vel = new Vector2(PartyImage.rand.nextDouble() * 0.8 + 0.125, 0),
      acc = Vector2.gravity + (Vector2.gravity * PartyImage.rand.nextDouble()),
      age = 0,
      green = PartyImage.rand.nextInt(100) + 50,
      red = false,
      gonnaBeRed = false
    )
  }

  def newRedParticle(): Particle = {
    new Particle(
      pos = new Vector2(PartyImage.rand.nextInt(ScalaFXDriver.WIDTH), 0),
      vel = new Vector2(PartyImage.rand.nextDouble() * 0.8 + 0.125, 0),
      acc = Vector2.gravity + (Vector2.gravity * PartyImage.rand.nextDouble()),
      age = 0,
      green = PartyImage.rand.nextInt(100) + 50,
      red = false,
      gonnaBeRed = true
    )
  }
}