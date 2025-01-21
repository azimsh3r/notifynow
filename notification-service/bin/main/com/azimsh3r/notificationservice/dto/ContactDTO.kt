import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ContactDTO @JsonCreator constructor(
    @JsonProperty("id") var id: Int,
    @JsonProperty("name") var name: String?,
    @JsonProperty("email") var email: String?,
    @JsonProperty("phoneNumber") var phoneNumber: String,
    @JsonProperty("templateId") var templateId: Int,
    @JsonProperty("extraInfo") var extraInfo: String?
)
