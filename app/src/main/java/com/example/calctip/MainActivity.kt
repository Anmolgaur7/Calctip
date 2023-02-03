package com.example.calctip

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.widget.Switch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calctip.ui.theme.CalcTipTheme
import kotlin.jvm.internal.Intrinsics.Kotlin

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
    val focusManager = LocalFocusManager.current
    var amountint by remember{ mutableStateOf("")}
    var tipInput by remember { mutableStateOf("")}
    var roundUp by remember { mutableStateOf(false) }
    val amount=amountint.toDoubleOrNull()?:0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount,tipPercent,roundUp)
 Column(modifier =Modifier.padding(35.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp))
 {
  Text(text = stringResource(R.string.head), color = Color.Red, fontWeight = FontWeight.Bold, fontSize =20.sp)
   Spacer(modifier = Modifier.height(20.dp))
   Editfield(value = amountint, onValueChange = {amountint=it}, label = R.string.label, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next), keyboardActions = KeyboardActions (onNext = { focusManager.moveFocus(FocusDirection.Down)}))
     Editfield(value = tipInput,onValueChange = {tipInput=it},label = R.string.tipp,keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),keyboardActions = KeyboardActions(onDone={ focusManager.clearFocus()}))
     RoundTheTipRow(roundUp = roundUp, onRoundUpChanged ={roundUp=it} )
     Spacer(modifier = Modifier.height(20.dp))
     Text(text = "Total Tip == $tip", fontWeight = FontWeight.Bold)
 }
}
@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Editfield(@StringRes label:Int,value: String,
              onValueChange: (String) -> Unit,modifier: Modifier=Modifier,keyboardOptions: KeyboardOptions,keyboardActions: KeyboardActions)
{
    TextField(value =value, onValueChange =onValueChange, label = { Text(text = stringResource(label))}, modifier =Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = keyboardOptions,keyboardActions=keyboardActions)
}
@RequiresApi(Build.VERSION_CODES.N)
private fun calculateTip(
    amount: Double,
    tipPercent: Double,
    roundUp:Boolean
):String {
    var tip = tipPercent / 100 * amount
    if (roundUp)
    {
    tip=kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}
@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )
    }
}
@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalcTipTheme {
        CalcTipscreen()
    }
}