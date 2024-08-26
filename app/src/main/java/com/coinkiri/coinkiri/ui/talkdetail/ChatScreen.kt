package com.coinkiri.coinkiri.ui.talkdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coinkiri.coinkiri.core.designsystem.theme.CoinkiriTheme
import com.coinkiri.coinkiri.ui.talkdetail.component.ChatContent
import com.coinkiri.coinkiri.ui.talkdetail.component.ChatScreenTopBar

@Composable
fun ChatScreen() {
    Scaffold(
        topBar = {
            ChatScreenTopBar()
        },
        content = { paddingValues -> // paddingValues 적용
            ChatContent(paddingValues)
        },
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    CoinkiriTheme {
        ChatScreen()
    }
}
