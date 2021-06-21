package com.example.conversion

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.conversion.database.Moneda
import java.lang.StringBuilder


fun formatMonedasToString(monedas: List<Moneda>, resources: Resources ):Spanned{

    val sb= StringBuilder()
    sb.apply {
        if(!monedas.isNullOrEmpty()) {
            monedas.forEach {
                append(resources.getString(R.string.formato_nro))
                append(" ${it.nro} - ")
                append(resources.getString(R.string.formato_moneda))
                append(" ${it.nombre} - ")
                append(resources.getString(R.string.formato_valor))
                append(" ${it.valor} <br>")
            }
        } else
            append(resources.getString(R.string.formato_no_hay_monedas))
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
