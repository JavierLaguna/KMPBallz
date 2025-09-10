package dev.jlaguna.kmpballz.business.models

data class Character(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: Gender,
    val description: String,
    val image: String,
    val affiliation: String,
    val transformations: List<CharacterTransformation>,
) {

    enum class Race(val displayName: String) {
        NUCLEICO_BENIGNO("Nucleico benigno"),
        NUCLEICO("Nucleico"),
        SAIYAN("Saiyan"),
        HUMAN("Human");

        override fun toString(): String = displayName

        companion object {
            fun fromDisplayName(name: String): Race? {
                return entries.find { it.displayName.equals(name, ignoreCase = true) }
            }
        }
    }

    enum class Gender(val displayName: String) {
        MALE("Male"),
        FEMALE("Female");

        override fun toString(): String = displayName

        companion object {
            fun fromDisplayName(name: String): Gender? {
                return entries.find { it.displayName.equals(name, ignoreCase = true) }
            }
        }
    }
}