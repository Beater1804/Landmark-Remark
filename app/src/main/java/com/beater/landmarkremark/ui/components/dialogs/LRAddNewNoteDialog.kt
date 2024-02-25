package com.beater.landmarkremark.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.beater.landmarkremark.R
import com.beater.landmarkremark.models.ui_actions.AddNewNoteAction
import com.beater.landmarkremark.models.ui_actions.AddNewNoteCallback
import com.beater.landmarkremark.ui.components.LRBasicTextField
import com.beater.landmarkremark.ui.components.LRMultipleLineTextField
import com.beater.landmarkremark.ui.components.LRRoundedButton
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.theme.LRButtonSize
import com.beater.landmarkremark.ui.theme.LRRoundedButtonStyle
import com.beater.landmarkremark.ui.theme.White
import com.beater.landmarkremark.utils.NOTE_DESCRIPTION_LENGTH

@Composable
fun LRAddNewDialog(
    onAction: AddNewNoteCallback
) {
    val context = LocalContext.current
    val focusManger = LocalFocusManager.current

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = {
            onAction(AddNewNoteAction.OnClose)
        }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(White)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LRText(
                    text = stringResource(R.string.add_new_note_for_current_location)
                )

                LRBasicTextField(
                    value = title,
                    placeholder = stringResource(R.string.title_placeholder),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions {
                        focusManger.moveFocus(FocusDirection.Down)
                    },
                ) {
                    title = it
                }

                LRMultipleLineTextField(
                    value = description,
                    placeholder = stringResource(R.string.description_placeholder),
                    maxLength = NOTE_DESCRIPTION_LENGTH,
                    fontSize = 18.sp,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier.height(200.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LRRoundedButton(
                        title = stringResource(R.string.cancel),
                        size = LRButtonSize.Primary,
                        style = LRRoundedButtonStyle.Negative,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        onAction(AddNewNoteAction.OnClose)
                    }

                    LRRoundedButton(
                        title = stringResource(R.string.save),
                        size = LRButtonSize.Primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        onAction(AddNewNoteAction.OnSave(title, description))
                        title = ""
                        description = ""
                    }
                }
            }
        }
    }
}