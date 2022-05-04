package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import elfak.mosis.myplaces.databinding.ActivityMyPlacesListBinding

class MyPlacesList : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPlacesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setTitle("My places")
        binding = ActivityMyPlacesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.my_places_list)
        val places = ArrayList<String>()
        places.add("Tvrdjava")
        places.add("Cair")
        places.add("Park Svetog Save")
        places.add("Trg Kralja Milana")
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, places)

        listView.adapter = arrayAdapter
        listView.setOnItemClickListener {
                _, _, i, _ ->
                Toast.makeText(this, "Item selected "+places[i], Toast.LENGTH_SHORT).show()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_places_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.show_map_item -> Toast.makeText(this, "Show map!", Toast.LENGTH_SHORT).show()
            R.id.new_place_item -> Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show()
            R.id.about_item -> {
                val i: Intent = Intent(this, About::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}