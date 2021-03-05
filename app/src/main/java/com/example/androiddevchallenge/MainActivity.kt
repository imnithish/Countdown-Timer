/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    val counterState = remember { mutableStateOf(4L) }
    val backgroundColor by animateColorAsState(if (counterState.value < 4L) Color.Red else MaterialTheme.colors.surface)

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "COUNTDOWN TIMER",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            },
            elevation = 0.dp,
            backgroundColor = backgroundColor
        )
    }, content = {
        Surface(
            color = backgroundColor
        ) {
            Content() {
                counterState.value = it
            }
        }
    })
}


@Composable
fun Content(count: (Long) -> Unit) {
    val inputValue = remember { mutableStateOf(0L) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val timer =
            object : CountDownTimer(inputValue.value * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    inputValue.value = (millisUntilFinished / 1000)
                    count((millisUntilFinished / 1000))
                }

                override fun onFinish() {
                    inputValue.value = 0L
                }
            }

        Text(
            text = "Enter time in seconds!",
            modifier = Modifier
                .padding(24.dp)
//                .background(color = backgroundColor)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = inputValue.value.toString(),
            onValueChange = { inputValue.value = it.toLong() },
            placeholder = {
                Text(
                    text = "0",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            },
            modifier = Modifier
                .padding(all = 16.dp)
                .width(230.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(12.dp)),
//                .background(color = backgroundColor)
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.width(230.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    timer.start()

                },
                modifier = Modifier
                    .height(40.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "START", fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

            Button(
                onClick = {
                    timer.cancel()
                },
                modifier = Modifier
                    .height(40.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "STOP", fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }


        }


    }


}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
