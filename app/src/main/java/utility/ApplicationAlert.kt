package utility

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

const val ALERT_ERROR = "Errore"
const val ALERT_INFO = "Informazione"

class ApplicationAlert {
    fun openDialog(type:String, msg: String, activityContext: AppCompatActivity, autoclose: Boolean) {
        val builder = AlertDialog.Builder(activityContext)

        builder.setTitle(type)
        builder.setMessage(msg)
        builder.setPositiveButton("OK") { _, _ ->
            if (autoclose)
                activityContext.finish()
        }

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}