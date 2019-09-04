package net.theluckycoder.expandablecardview.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.theluckycoder.expandablecardview.ExpandableCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card2 = findViewById<ExpandableCardView>(R.id.card2)
        card2.cardTitle = "Card with Action"
        card2.cardDescription = "An expandable card with an action button"
        card2.expandDuration = 300
        card2.setAction("Action", View.OnClickListener {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
            card2.collapse(true)
        })
    }
}
