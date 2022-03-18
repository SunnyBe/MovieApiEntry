package com.sundayndu.movieappentry

import com.sundayndu.movieappentry.utils.Constants
import org.junit.Assert
import org.junit.Test

class UtilitiesTest {

    @Test
    fun testImageUrlGenerationIsValidWithProvidedSize() {
        val expectedUrl = "https://image.tmdb.org/t/p/w500/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"
        val actual = Constants.makeImageUrl("/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg", "w500")
        Assert.assertEquals(expectedUrl, actual)
    }

    @Test
    fun testImageUrlGenerationIsValidWithoutSize() {
        val expectedUrl = "https://image.tmdb.org/t/p/w500/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg"
        val actual = Constants.makeImageUrl("/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg")
        Assert.assertEquals(expectedUrl, actual)
    }
}