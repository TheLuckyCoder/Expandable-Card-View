package net.theluckycoder.expandablecardview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import net.theluckycoder.expandablecardview.ExpandableCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card1 = findViewById<ExpandableCardView>(R.id.card1)
        card1.setExpandCollapseListener(findViewById(android.R.id.content))

        val card2 = findViewById<ExpandableCardView>(R.id.card2)
        card2.setExpandCollapseListener(findViewById(android.R.id.content))
        card2.cardTitle = "Card with Action"
        card2.cardDescription = "An expandable card with an action button"
        card2.setAction("Action", View.OnClickListener {
            Toast.makeText(this@MainActivity, "Button Clicked", Toast.LENGTH_SHORT).show()
        })
    }
}
