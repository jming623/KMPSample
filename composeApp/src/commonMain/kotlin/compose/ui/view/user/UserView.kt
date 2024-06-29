package compose.ui.view.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.domain.model.ApiResult
import compose.ui.component.NameCardBox

@Composable
fun UserView(
    vm: UserVM = viewModel()
) {
    val context = LocalContext.current
    val result by vm.users.collectAsState()
    val nickname by vm.nickname.collectAsState()
    val searchType by vm.searchType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is ApiResult.Loading -> CircularProgressIndicator()
                is ApiResult.Error -> Text(text = "${result.error}", color = Color.Red)
                is ApiResult.Success -> {
                    if(result.data.isNullOrEmpty()) {
                        Text(text = "Data Empty")
                    } else {
                        result.data!!.forEach {
                            NameCardBox(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                user = it
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Column {
                    TextField(value = nickname, onValueChange = { vm.emitNickname(it) })
                    Button(onClick = { vm.toggleSearchType() }) {
                        Text(text = "$searchType")
                    }
                }
                Button(onClick = { vm.getUsers() }) {
                    Text(text = "SEARCH")
                }
            }
        }
    }
}