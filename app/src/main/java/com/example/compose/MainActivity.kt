package com.example.compose

import android.os.Bundle
import android.view.View
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme
import kotlin.random.Random


var att: Int by mutableStateOf(0)
private var min = 1
private var max = 20
private var n = -1
private var attmax = 10


private val loseimg = R.drawable.lose
private val winimg = R.drawable.win

private var imgid by mutableStateOf(loseimg)
private var imgalp by mutableStateOf(0f)
private var resul by mutableStateOf(1)

var txtState by mutableStateOf("")


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startGame()
        setContent {
            ComposeTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally

                    )
                    {
                        Spacer(Modifier.height(40.dp))
                        Tv1(att)
                        Spacer(Modifier.height(40.dp))
                        TextEdit()
                        Spacer(Modifier.height(40.dp))
                        ResText()

                        Spacer(Modifier.height(40.dp))

                        ButtCheck()

                        Spacer(Modifier.height(40.dp))

                        ResImg(imgid, imgalp)

                        Spacer(Modifier.height(20.dp))

                        ButtPlayAgain(imgalp)
                    }
                }
            }
        }
    }
}

private fun generateNumber(): Int{
    return Random.nextInt(min, max)
}

private fun startGame(){
    imgalp = 0f
    n = generateNumber()
    resul = 2
    att = attmax
}

private fun checkNum(){
    if (txtState.toInt() == n){
        win()
    }
    else if(txtState.toInt() < n){
        resul = 4
    }
    else{
        resul = 3
    }
    att--
    txtState = ""

    if(att <= 0){
        lose()
    }
}

private fun win(){
    imgalp = 1f
    resul = 0
    imgid = winimg
}

private fun lose(){
    imgalp = 1f
    resul = 1
    imgid = loseimg
}

@Composable
fun ButtCheck(){
    Button(onClick = {
        if(att > 0 && txtState != "" && resul >= 2) {
            checkNum()
        }

    })
    {
        Text(text = "Проверить число", fontSize = 24.sp)
    }
}

@Composable
fun Tv1(att: Int){
    Text(
        text = "Количество попыток: $att",
        fontSize = 28.sp
    )
}

@Composable
fun ResImg(id: Int, vis: Float){
    Image(contentDescription = "", painter = painterResource(id),
        modifier = Modifier.size(250.dp)
            .alpha(vis)
        )
}

@Composable
fun ResText(){
    var txt = ""
    var alp = 0f
    if(resul == 0){
        txt = "Вы победили!"
        alp = 1f
    }
    if(resul == 1){
        txt = "Вы проиграли!"
        alp = 1f
    }
    if(resul == 2){
        txt = "/"
        alp = 0f
    }
    if(resul == 3){
        txt = "Нужно число меньше"
        alp = 1f
    }
    if(resul == 4){
        txt = "Нужно число больше"
        alp = 1f
    }
    Text(text = txt, modifier = Modifier.alpha(alp), fontSize = 26.sp)
}

@Composable
fun TextEdit(){

    TextField(
        value = txtState,
        onValueChange = {newText ->
            txtState = newText.filter { it.isDigit()
        }},
        placeholder = {
            Text(text = "Введите число (от $min до $max) :")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun ButtPlayAgain(alp: Float){
    Button(onClick = {startGame()}, modifier = Modifier.alpha(alp), enabled = alp > 0f)
    {
        Text(text = "Играть снова", fontSize = 24.sp)

    }
}


