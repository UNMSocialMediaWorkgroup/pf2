package edu.unm.carc.socialmedia.party.post

import edu.unm.carc.socialmedia.party.util.CollectionUtil

import scala.collection.parallel.mutable.ParArray

/**
 * Created by sixstring982 on 5/10/15.
 */
object AverageBlur {
  def blur(pixels: ParArray[Int], width: Int,
           height: Int, boxSize: Int): ParArray[Int] = {
    ParArray.tabulate[Int](width * height) {
      i: Int ⇒ {
        val x = i % width
        val y = i / width

        x > boxSize && y > boxSize &&
        x < width - boxSize && y < height - boxSize match {
          case false ⇒ pixels(i)
          case true ⇒
            CollectionUtil.average(
            Array.tabulate[Int](boxSize, boxSize) {
              (xx: Int, yy: Int) ⇒ {
                pixels(xx - boxSize / 2 + x +
                      (yy - boxSize / 2 + y) * width)
              }
            }.flatten)
        }
      }
    }
  }
}
