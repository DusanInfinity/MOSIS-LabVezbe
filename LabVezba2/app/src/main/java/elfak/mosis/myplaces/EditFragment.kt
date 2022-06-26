package elfak.mosis.myplaces

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.model.LocationViewModel
import elfak.mosis.myplaces.model.MyPlacesViewModel


class EditFragment : Fragment() {

    private val locationViewModel: LocationViewModel by activityViewModels()
    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_my_places_list -> {
                this.findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_new_place)
        item.isVisible = false;
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editName: EditText = requireView().findViewById(R.id.editmyplace_name_edit)
        val editDesc: EditText = requireView().findViewById(R.id.editmyplace_desc_edit)

        val editLongitude: EditText = requireView().findViewById(R.id.editmyplace_longitude_edit)
        val lonObserver = Observer<String> { newValue -> editLongitude.setText(newValue.toString())}
        locationViewModel.longitude.observe(viewLifecycleOwner, lonObserver);

        val editLatitude: EditText = requireView().findViewById(R.id.editmyplace_latitude_edit)
        val latObserver = Observer<String> { newValue -> editLatitude.setText(newValue.toString())}
        locationViewModel.latitude.observe(viewLifecycleOwner, latObserver);

        if(myPlacesViewModel.selected!=null){
            editName.setText(myPlacesViewModel.selected?.name)
            editDesc.setText(myPlacesViewModel.selected?.description)
            editLongitude.setText(myPlacesViewModel.selected?.longitude)
            editLatitude.setText(myPlacesViewModel.selected?.latitude)
        }
        val addButton: Button = requireView().findViewById<Button>(R.id.editmyplace_finished_button)
        addButton.isEnabled = false
        if(myPlacesViewModel.selected!=null) {
            addButton.setText(R.string.editmyplace_save_label)
            addButton.isEnabled = true
        }
        else (requireActivity() as AppCompatActivity).supportActionBar?.title = "Add My Place"
        editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                addButton.isEnabled = (editName.text.isNotEmpty())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        addButton.setOnClickListener {
            val name: String = editName.text.toString()
            val desc: String = editDesc.text.toString()
            val longitude: String = editLongitude.text.toString()
            val latitude: String = editLatitude.text.toString()
            if(myPlacesViewModel.selected!=null){
                myPlacesViewModel.selected?.name = name
                myPlacesViewModel.selected?.description = desc
                myPlacesViewModel.selected?.longitude = longitude
                myPlacesViewModel.selected?.latitude = latitude
            }
            else{
                myPlacesViewModel.addPlace(MyPlace(name, desc, longitude, latitude))
            }
            myPlacesViewModel.selected = null
            locationViewModel.setLocation("", "")
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
            //findNavController().popBackStack()
        }
        val cancelButton: Button = requireView().findViewById(R.id.editmyplace_cancel_button)
        cancelButton.setOnClickListener {
            myPlacesViewModel.selected = null
            locationViewModel.setLocation("", "")
            //findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
            findNavController().popBackStack()
        }
        val setButton: Button = requireView().findViewById<Button>(R.id.editmyplace_location_button)
        setButton.setOnClickListener {
            locationViewModel.setLocation = true;
            findNavController().navigate(R.id.action_EditFragment_to_MapFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}