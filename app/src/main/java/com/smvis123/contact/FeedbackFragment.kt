package com.smvis123.contact


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.base.BaseFragment
import com.smvis123.databinding.FragmentFeedbackBinding

class FeedbackFragment : BaseFragment() {
    private val sexArray = arrayOf("Male", "Female")
    private val countryArray = arrayOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antigua & Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia & Herzegovina",
        "Botswana",
        "Brazil",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Cape Verde",
        "Cayman Islands",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Congo",
        "Cook Islands",
        "Costa Rica",
        "Cote D Ivoire",
        "Croatia",
        "Cruise Ship",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Polynesia",
        "French West Indies",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guam",
        "Guatemala",
        "Guernsey",
        "Guinea",
        "Guinea Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kuwait",
        "Kyrgyz Republic",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macau",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Norway",
        "Oman",
        "Pakistan",
        "Palestine",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Reunion",
        "Romania",
        "Russia",
        "Rwanda",
        "Saint Pierre & Miquelon",
        "Samoa",
        "San Marino",
        "Satellite",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "South Africa",
        "South Korea",
        "Spain",
        "Sri Lanka",
        "St Kitts & Nevis",
        "St Lucia",
        "St Vincent",
        "St. Lucia",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Timor L'Este",
        "Togo",
        "Tonga",
        "Trinidad & Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks & Caicos",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "Uruguay",
        "Uzbekistan",
        "Venezuela",
        "Vietnam",
        "Virgin Islands (US)",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )
    private lateinit var binding: FragmentFeedbackBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val countryAdapter =
            activity?.let { ArrayAdapter(it, R.layout.my_spinner_textview_white, countryArray) }
        countryAdapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner1.adapter = countryAdapter
        binding.spinner1.setSelection(86)

        val sexAdapter =
            activity?.let { ArrayAdapter(it, R.layout.my_spinner_textview_white, sexArray) }
        sexAdapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = sexAdapter
        binding.buttonSend.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phNo = binding.editTextPhno.text.toString()
            val message = binding.editTextMessage.text.toString()
            val sex = sexArray[binding.spinner2.selectedItemPosition]
            val country = countryArray[binding.spinner1.selectedItemPosition]
            if (!TextUtils.isEmpty(name)) {
                binding.editTextName.error = null
                if (!TextUtils.isEmpty(message)) {
                    binding.editTextMessage.error = null
                    sendFeedBack(name, email, message, sex, country, phNo)
                    clearEditText()
                } else binding.editTextMessage.error = getString(R.string.required)
            } else binding.editTextName.error = getString(R.string.required)


        }
    }

    private fun clearEditText() {
        binding.spinner1.setSelection(86)
        binding.editTextName.setText("")
        binding.editTextEmail.setText("")
        binding.editTextMessage.setText("")
        binding.editTextPhno.setText("")
    }

    private fun sendFeedBack(
        name: String,
        email: String,
        message: String,
        sex: String,
        country: String,
        phNo: String
    ) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
            intent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK")
            intent.putExtra(
                Intent.EXTRA_TEXT, name + "\n"
                        + sex + "\n"
                        + email + "\n"
                        + phNo + "\n"
                        + country + "\n\n"
                        + "Message :  " + message
            )

            intent.setDataAndType(
                Uri.parse("mailto: gospelvisiontvsl@gmail.com"),
                "text/plain"
            ) // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }


}
