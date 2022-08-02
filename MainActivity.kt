package com.example.tiptome

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptome.ui.theme.TipTomeTheme
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@IgnoreExtraProperties


class MainActivity : ComponentActivity() {
private lateinit var database: DatabaseReference
    // [END declare_database_ref]

    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        // [END initialize_database_ref]
    }

    private fun addPostEventListener(postReference: DatabaseReference) {
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue<Post>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}



@Composable
fun TipTimeScreen(){
    var makeInput by remember {
        mutableStateOf("")
    }
    var yearInput by remember {
        mutableStateOf("")
    }
    var carModelInput by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // align modifier to center the text
        Text(text = stringResource(id = R.string.calculate_tip), //the text
            fontSize = 24.sp, //the size of the text
            modifier = Modifier.align(Alignment.CenterHorizontally)// the alignment of the text
        )
       // space between the title and text field
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberField(label = R.string.bill_amount, value = makeInput, onValueChanged = {makeInput = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next), keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ))

        Spacer(modifier = Modifier.height(16.dp))
        EditYearField(label = R.string.car_year, value = yearInput, onValueChanged = {yearInput = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)}))

        Spacer(modifier = Modifier.height(16.dp))
        EditModelField(label = R.string.model_name, value = carModelInput, onValueChanged = {carModelInput = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)}) )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.tip_amount),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



// use text fill composable to accept input from the user
@Composable
fun EditNumberField(@StringRes label: Int, keyboardOptions: KeyboardOptions, keyboardActions: KeyboardActions,
    value: String, onValueChanged: (String) -> Unit, modifier: Modifier = Modifier) {
    //this leaves the text box empty
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )

}

@Composable
fun EditYearField(@StringRes label: Int, keyboardOptions: KeyboardOptions, keyboardActions: KeyboardActions,
                  value: String, onValueChanged: (String) -> Unit, modifier: Modifier = Modifier){
    //this leaves the textbox empty
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun EditModelField(@StringRes label: Int, keyboardOptions: KeyboardOptions, keyboardActions: KeyboardActions,
                   value: String, onValueChanged: (String) -> Unit, modifier: Modifier = Modifier){
    //this leaves the text box empty
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTomeTheme {
       TipTimeScreen()
    }
}