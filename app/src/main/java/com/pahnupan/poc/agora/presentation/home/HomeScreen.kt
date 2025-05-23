import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pahnupan.poc.agora.presentation.home.HomeEvent
import com.pahnupan.poc.agora.presentation.home.HomeIntent
import com.pahnupan.poc.agora.presentation.home.HomeViewModel
import com.pahnupan.poc.agora.presentation.home.composable.PermissionScreen
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val event = viewModel.event.receiveAsFlow().collectAsState(HomeEvent.Init).value
    var allowPermission by remember { mutableStateOf(false) }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            allowPermission = true
        } else {
            showToast(context, "Please Allow Permission On Setting App")
        }
    }

    LaunchedEffect(true) {
        if (viewModel.permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            allowPermission = true
        } else {
            launcherMultiplePermissions.launch(viewModel.permissions)
        }
    }
    LaunchedEffect(event) {
        when (event) {
            HomeEvent.Init -> {

            }

            HomeEvent.OnBookingRoomError -> {
                showToast(context, "something wrong")
            }

            is HomeEvent.OnBookingRoomSuccess -> {
                showToast(context, "create success")
            }
        }

    }

    HomeScreen(
        allowPermission = allowPermission,
        onClickPermission = {
            launcherMultiplePermissions.launch(viewModel.permissions)
        },
        onClickButton = {
            viewModel.emitIntent(HomeIntent.BookingQueue)
        }
    )
}

private fun showToast(context: Context, mes: String) {
    Toast.makeText(context, mes, Toast.LENGTH_SHORT).show()
}

@Composable
private fun HomeScreen(
    allowPermission: Boolean,
    onClickPermission: () -> Unit,
    onClickButton: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) { }
        if (allowPermission) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = "สวัสดีคนไข้ กดนัดหมอได้เลย !",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Normal
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onClickButton) {
                    Text("จอง")
                }

            }
        } else {
            PermissionScreen(
                modifier = Modifier.fillMaxSize(),
                onClickRequirePermission = onClickPermission
            )
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        allowPermission = true,
        onClickPermission = {}
    )
}

@Preview
@Composable
fun PreviewHomeScreenRequirePermission() {
    HomeScreen(
        allowPermission = false,
        onClickPermission = {}
    )
}