package dev.jlaguna.kmpballz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadingIndicator(
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    if (enabled) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

                Text(stringResource(Res.string.loading))
            }
        }
    }
}