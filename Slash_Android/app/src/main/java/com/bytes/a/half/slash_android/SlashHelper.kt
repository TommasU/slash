package com.bytes.a.half.slash_android

object SlashHelper {
    fun getCompanyLogoUrl(companyName: String) : String {
        return when (companyName) {
            SlashConstants.COMPANY_AMAZON -> {
                SlashConstants.AMAZON_LOGO_URL
            }

            SlashConstants.COMPANY_WALMART -> {
                SlashConstants.WALMART_LOGO_URL
            }

            SlashConstants.COMPANY_ETSY -> {
                SlashConstants.ETSY_LOGO_URL
            }

            SlashConstants.COMPANY_BJS -> {
                SlashConstants.BJS_LOGO_URL
            }

            SlashConstants.COMPANY_TARGET -> {
                SlashConstants.TARGET_LOGO_URL
            }

            else -> {
                SlashConstants.COMPANY_LOGO_URL
            }

        }
    }
}