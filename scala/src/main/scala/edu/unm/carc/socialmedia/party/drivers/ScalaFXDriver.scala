package edu.unm.carc.socialmedia.party.drivers

import edu.unm.carc.socialmedia.party.image.PartyImage
import edu.unm.carc.socialmedia.party.util.ScalaFXUtil

import scalafx.Includes.handle
import scalafx.application.{Platform, JFXApp}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.ImageView

/**
 * Created by sixstring982 on 4/22/15.
 */
object ScalaFXDriver extends JFXApp {

  val WIDTH = 1504
  val HEIGHT = 150

  val img = new PartyImage

  stage = new PrimaryStage {
    title.value = "Party, The Scala Particle System"
    width = WIDTH
    height = HEIGHT
    scene = new Scene {
      content = new ImageView {
        image = img
      }

      onMouseClicked = handle {
        ScalaFXUtil.runLater({
          () ⇒ img.addImpurities()
        })
      }
    }

    onCloseRequest = handle {
      Platform.exit()
    }
  }
}
