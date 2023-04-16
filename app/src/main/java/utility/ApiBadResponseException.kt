package utility

class ApiBadResponseException(responseCode: Int) : Exception("API call fallita con errore $responseCode")