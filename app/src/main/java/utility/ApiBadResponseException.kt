package utility

class ApiBadResponseException(responseCode: Int) : Exception("API call with error: $responseCode")