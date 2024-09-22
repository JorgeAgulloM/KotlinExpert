package extras

data class MyNote(val name: String, val description: String)
data class MyNote2(val name: String, val description: String) : Encrypted {
    override fun getEncryptedString(): String {
        /****/
        return "Encrypted"
    }

}

class EncrypterGeneric<T>(val item: T) {
    fun run(): String {
        return "Encrypted"
    }
}

class EncrypterGenericNotNull<T : Any>(val item: T) {
    fun run(): String {
        return "Encrypted"
    }
}

interface Encrypted {
    fun getEncryptedString(): String
}

class EncrypterGenericSpecific<T : Encrypted>(private val item: T) {
    fun run(): String {
        return item.getEncryptedString()
    }
}



fun main(note: MyNote?) {
    val encrypter = EncrypterGeneric(note)
    val res = encrypter.run()

    val noteNotNull = note!!
    val encrypterGenericNotNull = EncrypterGenericNotNull(noteNotNull)

    val noteWithEncryp = MyNote2("John", "TerminatorSavior")
    val encrypterGenericGeneric = EncrypterGenericSpecific(noteWithEncryp)
}