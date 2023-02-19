package com.example.notesapplication

import android.graphics.drawable.VectorDrawable
import android.media.Image
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapplication.destinations.*
import com.example.notesapplication.model.Note
import com.example.notesapplication.ui.AddNoteVM
import com.example.notesapplication.ui.DeleteNoteVM
import com.example.notesapplication.ui.ShowNotesVM
import com.example.notesapplication.ui.theme.*
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.random.Random
import kotlin.random.nextInt


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesApplicationTheme() {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }

        }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun ShowNotesScreen(
    navigator: DestinationsNavigator,
    viewModel: ShowNotesVM= hiltViewModel()
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .align(Alignment.TopCenter)
            .padding(10.dp)){

            val openDialog = remember { mutableStateOf(false) }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    text = {
                        Text(
                            "Notes App Designed by Islam :) Used to Save, Add, Delete, & show notes."
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { openDialog.value = false }
                            ) {
                                Text("Enjoy!")
                            }
                        }
                    }
                )
            }

            Text(
                text = "Notes", fontSize = 40.sp, fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.width(185.dp))
            Icon(imageVector = Icons.Default.Search, contentDescription =null,
                Modifier
                    .size(35.dp)
                   )
            Icon(imageVector = Icons.Default.Info, contentDescription =null,
                Modifier
                    .size(30.dp)
                    .clickable {
                        openDialog.value = true
                    })
        }
        if( viewModel.notes.size==0){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Hello ..", fontSize =40.sp, color = Color(0xFFE57373 )
                    , fontWeight = FontWeight.Bold)
                Text(text = "it seems like that's your first time here")
                Image(painterResource(id = R.drawable.mobile_note_list_pana), contentDescription = null)
                Text(text = "Create your first note :)")
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    onClick = {
                        navigator.navigate(AddNoteScreenDestination)
                    }) {
                    Text(text = "+", fontSize = 35.sp)
                }
            }
        }else{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
        ){
            items(viewModel.notes){note ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable { navigator.navigate(NoteBodyScreenDestination(note)) }
                    ,RoundedCornerShape(15))
                {
                    var listColors = mutableListOf(c1,c4,c9,c10)
                    var i=Random.nextInt(4)
                    var c=listColors[i]
                Column(modifier = Modifier.background(color=  c)){
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text ="${note.title}", fontSize=25.sp,modifier= Modifier.padding(start=15.dp))
                    Button(modifier= Modifier
                        .padding(start = 300.dp, bottom = 5.dp)
                        .size(50.dp)
                        .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        onClick = {
                            navigator.navigate(DeleteNoteScreenDestination(note))
                        }
                   ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null,
                           modifier = Modifier.fillMaxSize())
                    }
                }

            }
        }
        }
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    onClick = {
                        navigator.navigate(AddNoteScreenDestination)
                    }) {
                    Text(text = "+", fontSize = 35.sp)
                }
            }}
}

@Destination
@Composable
fun AddNoteScreen(
    navigator: DestinationsNavigator,
    viewModel: AddNoteVM = hiltViewModel()
){
    var title by remember {
        mutableStateOf("")
    }
    var notee by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
    Row(
        Modifier.padding(25.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        TextField(modifier = Modifier.size(500.dp,100.dp),
            value = title, onValueChange = { title=it }, placeholder = {
                Text(
                    text = "Title..", fontSize = 35.sp,
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            })
    }
        TextField(modifier = Modifier
            .size(500.dp, 500.dp)
            .padding(15.dp)
            ,value = notee, onValueChange = { notee= it }, placeholder = {
                Text(
                    text = "Your note here..", fontSize = 20.sp,
                    color = Color.Gray, fontWeight = FontWeight.Medium
                )})

        Row(
            Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = {
                viewModel.AddNote(Note(title =title, note_text = notee ))
                navigator.navigate(ShowNotesScreenDestination)
            }) {
                Text(text = "Add")
            }
            Button(
                modifier =Modifier.padding(horizontal = 15.dp),
                onClick = {
                    navigator.navigate(ShowNotesScreenDestination)
                }) {
                Text(text = "Back")
            }
        }

}}

@Destination
@Composable
fun DeleteNoteScreen(
    navigator: DestinationsNavigator,
    viewModel: DeleteNoteVM = hiltViewModel(),
    note:Note
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Are You Sure Deleting this Great Item ? :(",
            fontSize = 27.sp,
            color = Color.Gray)
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            onClick = {
                viewModel.DeleteNote(note)
                navigator.navigate(ShowNotesScreenDestination)
        }) {
            Text(text = "Delete!")
        }
        Button(
            modifier =Modifier.padding(horizontal = 15.dp),
            onClick = {
                navigator.navigate(ShowNotesScreenDestination)
            }) {
            Text(text = "Back")
        }
    }

}

@Destination
@Composable
fun NoteBodyScreen(
    navigator: DestinationsNavigator,
    viewModel: AddNoteVM = hiltViewModel(),
    note:Note
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(6.dp)
    ){

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Black),
            onClick = {
                navigator.navigate(ShowNotesScreenDestination)
            }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(20.dp))

            Text(
//                modifier = Modifier
//                    .size(500.dp, 100.dp),
                text = "${note.title}", fontSize = 35.sp,
                color = Color.DarkGray, fontWeight = FontWeight.Bold
            )
        Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier
//                .size(500.dp, 500.dp)
                .padding(top = 20.dp),
                text = "${note.note_text}", fontSize = 20.sp,
                color = Color.Gray, fontWeight = FontWeight.Medium
            )
        }
        }}

