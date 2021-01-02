package com.example.uniroomf.utilityClasses

class PrenInfo
{
    // Classe che contiene gli elementi di ogni prenotazione completa di getter e setter

    /*
        Gli elementi che dobbiamo visualizzare sono:
        1. Aula
        2. Data
        3. Ora inizio
        4. Ora Fine
     */

    private var aula = ""
    private var dataPren = " "
    private var oraInizio = " "
    private var oraFine = " "
    private var posto = " "
    private var tipo = " "
    private var datiDoc = " "

    // Implemento i getter e setter necessari per rispettare l'Information Hiding

    fun getAula() : Any?
    {
        return this.aula
    }

    fun getDataPren() : String?
    {
        return this.dataPren
    }

    fun getOraInizio() : String?
    {
        return this.oraInizio
    }

    fun getOraFine() : String?
    {
        return this.oraFine
    }

    fun getPosto() : String?
    {
        return this.posto
    }

    fun getTipo() : String?
    {
        return this.tipo
    }

    fun getDocente() : String?
    {
        return this.datiDoc
    }

    // Setter

    fun setAula(a : String)
    {
        this.aula = a
    }

    fun setDataPren(s:String)
    {
        this.dataPren = s
    }

    fun setOraInizio(s:String)
    {
        this.oraInizio = s
    }

    fun setOraFine(s:String)
    {
        this.oraFine = s
    }

    fun setPosto(s : String)
    {
        this.posto = s
    }

    fun setTipo(s : String)
    {
        this.tipo = s
    }

    fun setDoc(s : String)
    {
        this.datiDoc = s
    }

}