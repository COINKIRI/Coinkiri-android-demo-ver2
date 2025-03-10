package com.coinkiri.coinkiri.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import com.coinkiri.coinkiri.R
import com.coinkiri.coinkiri.core.designsystem.component.dialog.ConfirmationDialog
import com.coinkiri.coinkiri.core.designsystem.component.topappbar.CoinkiriTopBar
import com.coinkiri.coinkiri.core.designsystem.theme.CoinkiriTheme
import com.coinkiri.coinkiri.core.designsystem.theme.White
import com.coinkiri.coinkiri.ui.profile.component.OptionItem
import com.coinkiri.coinkiri.ui.profile.component.SettingOptionsItem
import com.coinkiri.coinkiri.ui.profile.component.UserInfoItem
import com.coinkiri.coinkiri.ui.profile.model.UserInfoModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    var showCheckLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.profileSideEffects, lifecycleOwner) {
        viewModel.profileSideEffects
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is ProfileSideEffect.LogoutSuccess -> {
                        navigateToLogin()
                    }

                    is ProfileSideEffect.LogoutFailure -> {
                        // 로그아웃 실패 시의 처리
                    }
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    val userInfo by viewModel.userInfo.collectAsState()

    ProfileScreen(
        onBackClick = onBackClick,
        onLogOutClick = { showCheckLogoutDialog = true },
        userInfo = userInfo
    )

    if (showCheckLogoutDialog) {
        ConfirmationDialog(
            title = "로그아웃 확인",
            message = "정말 로그아웃 하시겠습니까?",
            confirmButtonText = "확인",
            dismissButtonText = "취소",
            onConfirm = {
                showCheckLogoutDialog = false
                viewModel.logout()
            },
            onDismiss = { showCheckLogoutDialog = false }
        )
    }
}

@Composable
private fun ProfileScreen(
    onBackClick: () -> Unit,
    onLogOutClick: () -> Unit,
    userInfo: UserInfoModel
) {

    Scaffold(
        topBar = {
            ProfileTopBar(
                onBackClick = onBackClick
            )
        },
        content = { padding ->
            ProfileContent(
                padding = padding,
                onLogOutClick = onLogOutClick,
                userInfo = userInfo
            )
        }
    )
}

@Composable
private fun ProfileTopBar(
    onBackClick: () -> Unit
) {
    CoinkiriTopBar(
        title = stringResource(id = R.string.profile),
        isShowBackButton = true,
        onBackClick = onBackClick
    )
}

@Composable
private fun ProfileContent(
    padding: PaddingValues,
    onLogOutClick: () -> Unit,
    userInfo: UserInfoModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(White)
    ) {
        UserInfoItem(userInfo)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(10.dp),
        ) {
            SettingOptionsItem(title = "App 설정") {
                OptionItem(
                    text = "테마설정",
                    onOptionClick = { /*TODO*/ }
                )
            }

            SettingOptionsItem(title = "고객지원") {
                OptionItem(
                    text = "정보수정",
                    onOptionClick = { /*TODO*/ }
                )
                OptionItem(
                    text = "로그아웃",
                    onOptionClick = onLogOutClick
                )
                OptionItem(
                    text = "회원탈퇴",
                    onOptionClick = { /*TODO*/ }
                )
            }

            SettingOptionsItem(title = "기타") {
                OptionItem(
                    text = "약관 및 개인정보 취급방침",
                    onOptionClick = { /*TODO*/ }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    CoinkiriTheme {
        ProfileScreen(
            onBackClick = {},
            onLogOutClick = {},
            userInfo = UserInfoModel()
        )
    }
}
