package dev.jlaguna.kmpballz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.jlaguna.kmpballz.business.models.CharacterTransformation
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.characterDetail_ki
import kmpballz.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.stringResource

@Composable
fun TransformationAlertDialog(
    transformation: CharacterTransformation,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = transformation.name) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = transformation.image,
                    contentDescription = transformation.name,
                    modifier = Modifier
                        .size(200.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "${stringResource(Res.string.characterDetail_ki)}: ${transformation.ki}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.ok))
            }
        }
    )
}