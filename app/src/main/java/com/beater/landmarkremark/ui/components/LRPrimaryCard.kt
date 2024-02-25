import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.beater.landmarkremark.ui.theme.CardShaw
import com.beater.landmarkremark.ui.theme.White

@Composable
fun LRPrimaryCard(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement,
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                spotColor = CardShaw,
                ambientColor = CardShaw
            )
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(24.dp)
    ) {
        content()
    }
}