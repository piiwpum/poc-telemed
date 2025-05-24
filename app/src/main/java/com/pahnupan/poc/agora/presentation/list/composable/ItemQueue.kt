package com.pahnupan.poc.agora.list.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pahnupan.poc.agora.composable.theme.Typography

@Composable
internal fun ItemQueue(
    modifier: Modifier,
    name: String,
    queueNo: Int,
    onClickItem: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(color = Color.White)
            .clickable {
                onClickItem.invoke()
            }
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = Color.LightGray.copy(alpha = 0.5f),
                spotColor = Color.LightGray.copy(alpha = 0.5f)
            )

            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier
                .background(color = Color.LightGray, shape = CircleShape)
                .padding(8.dp),
            imageVector = Icons.Rounded.Person, contentDescription = "avatar"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = name,
            style = Typography.titleMedium
        )
        Text(text = "no.${queueNo}", style = Typography.titleMedium)
    }
}


@Preview
@Composable
private fun ItemQueuePreview() {
    Column(
        modifier = Modifier.background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ItemQueue(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            name = "Piiw Number one",
            queueNo = 1,
        )
        ItemQueue(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            name = "Denchai Jaidee",
            queueNo = 2,
        )
        ItemQueue(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            name = "Easy joker za",
            queueNo = 3,
        )
    }

}