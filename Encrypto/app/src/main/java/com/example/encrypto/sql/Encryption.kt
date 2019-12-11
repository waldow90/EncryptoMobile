package com.example.encrypto.sql

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class Encryption {

    private val iterations = 17392
    private val saltSize = 256
    private val hashSize = 256
    private val add = "qyqyqyqyqyqyqyqy"

    fun encryptPin(input: String): String {
        val salt = generateRandomSalt()
        val hash = generateRandomHash(input, salt)

        return fromByteArrayToString(salt) + ":" + fromByteArrayToString(hash!!)
    }

    private fun fromByteArrayToString(input: ByteArray): String? {
        return input.toString(Charsets.US_ASCII)
    }

    private fun fromStringToByteArray(input: String): ByteArray? {
        return input.toByteArray(Charsets.US_ASCII)

    }

    fun checkPin(savedPin: String, input: String): Boolean {
        val parts = savedPin.split(":")
        val salt = fromStringToByteArray(parts[0])
        val hash = fromStringToByteArray(parts[1])
        val inputhash = generateRandomHash(input, salt!!)
        return inputhash?.contentEquals(hash!!)!!
    }

    fun encrypt(input: String, savedPin: String): ByteArray {
        val salt = generateRandomSalt()
        val iv = generateRandomIv()
        val ivSpec = IvParameterSpec(iv)

        val keyBytes = generateRandomHash(savedPin, salt)

        val keySpec = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        val fixedinput: String = if (input.length <= 16){
            input.plus(add)
        } else{
            input
        }

        val encrypted = cipher.doFinal(fromStringToByteArray(fixedinput))

        val result = ByteArray(304)
        System.arraycopy(salt, 0, result, 0, saltSize)
        System.arraycopy(iv, 0, result, saltSize, 16)
        System.arraycopy(encrypted, 0, result, saltSize + 16, 32)
        return result
    }

    fun decrypt(input: ByteArray, savedPin: String): String {

        val salt = ByteArray(256)
        val iv = ByteArray(16)
        val encrypted = ByteArray(32)
        System.arraycopy(input, 0, salt, 0, saltSize)
        System.arraycopy(input, saltSize, iv, 0, 16)
        System.arraycopy(input, saltSize + 16, encrypted, 0, 32)
        val ivSpec = IvParameterSpec(iv)

        val keyBytes = generateRandomHash(savedPin, salt)
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        var decrypted = fromByteArrayToString(cipher.doFinal(encrypted))

        if (decrypted?.endsWith(add)!!){
            decrypted = decrypted.take(decrypted.length - 16)
        }

        return decrypted
    }

    private fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(saltSize)
        random.nextBytes(salt)
        return salt
    }

    private fun generateRandomHash(password: String, salt: ByteArray): ByteArray? {
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, iterations, hashSize)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBEwithHmacSHA512AndAES_256")
        return secretKeyFactory.generateSecret(pbKeySpec).encoded
    }

    private fun generateRandomIv(): ByteArray {
        val random = SecureRandom()
        val iv = ByteArray(16)
        random.nextBytes(iv)
        return iv
    }
}