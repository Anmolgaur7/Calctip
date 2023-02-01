package com.example.calctip

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calctip.ui.theme.CalcTipTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcTipTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    CalcTipscreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CalcTipscreen()
{
    var amountint by remember{ mutableStateOf("")}
    val amount=amountint.toDoubleOrNull()?:0.0
    val tip = calculateTip(amount)
 Column(modifier =Modifier.padding(35.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp))
 {
  Text(text = stringResource(R.string.head), color = Color.Red, fontWeight = FontWeight.Bold, fontSize =20.sp)
   Spacer(modifier = Modifier.height(20.dp))
   Editfield(value = amountint, onValueChange = {amountint=it})
     Spacer(modifier = Modifier.height(20.dp))
   Text(text = "Total Tip == $tip", fontWeight = FontWeight.Bold)
 }
}
@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Editfield(value: String,
              onValueChange: (String) -> Unit)
{
    TextField(value =value, onValueChange =onValueChange, label = { Text(text = stringResource(R.string.label))}, modifier =Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
}
@RequiresApi(Build.VERSION_CODES.N)
private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0
):String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalcTipTheme {
        CalcTipscreen()
    }
}