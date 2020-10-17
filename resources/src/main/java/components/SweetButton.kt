package components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton


class SweetButton : AppCompatButton {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)

    }
}