package edu.unm.carc.socialmedia.party.physics

/**
 * Created by sixstring982 on 4/22/15.
 */
case class Vector2(x: Double, y: Double) {
  def -(that: Vector2): Vector2 = new Vector2(x - that.x, y - that.y)

  def +(that: Vector2): Vector2 = new Vector2(x + that.x, y + that.y)

  def *(that: Double): Vector2 = new Vector2(x * that, y * that)

  def distance(that: Vector2): Double = {
    val dx = x - that.x
    val dy = y - that.y
    Math.sqrt(dx * dx + dy * dy)
  }

  def length(): Double = distance(Vector2.empty)

  def normalize(): Vector2 = this * (1.0 / length)
}

object Vector2 {
  val empty: Vector2 = new Vector2(0, 0)
  val gravity: Vector2 = new Vector2(0, 0.0001)
  val unitX: Vector2 = new Vector2(1, 0)
  val unitY: Vector2 = new Vector2(0, 1)
}
