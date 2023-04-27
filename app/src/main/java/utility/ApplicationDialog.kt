/***************************************************************************************************
 * ApplicationDialog
 * Classe che contiene metodi per la creazione di Dialog specifici per la nostra app, basati sul
 * Dialog previsto da Android Compat.
 **************************************************************************************************/

package utility

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

const val ALERT_ERROR = "Errore"
const val ALERT_INFO = "Informazione"

class ApplicationDialog {
    /*
    Crea un dialog avente un titolo e un messaggio personalizzato.
    Con l'attributo autoclose è possibile decidere se la chiusura del dialog comporta la chiusura
    dell'applicazione.
     */
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