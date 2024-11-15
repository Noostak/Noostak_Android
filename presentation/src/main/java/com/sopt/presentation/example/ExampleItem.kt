package com.sopt.presentation.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sopt.domain.entity.ExampleEntity

@Composable
fun ExampleItem(
    data: ExampleEntity
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(vertical = 10.dp, horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = data.firstName)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = data.email)
            }
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = data.avatar,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExampleItemPreview() {
    ExampleItem(
        data = ExampleEntity(
            firstName = "John",
            email = "",
            avatar = ""
        )
    )
}