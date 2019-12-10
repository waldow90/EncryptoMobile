package com.example.encrypto.sql

import android.util.Base64
import android.util.Log.d
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class Encryption {

    //private val iterations = 173924
    private val iterations = 1
    private val saltSize = 256
    private val hashSize = 256

    fun encryptPin(input: String): String {
        val salt = generateRandomSalt()
        val hash = generateRandomHash(input, salt)

        return fromByteArrayToString(salt) + ":" + fromByteArrayToString(hash!!)
    }

    private fun fromByteArrayToString(input: ByteArray): String? {
        return Base64.encodeToString(input, Base64.NO_WRAP)
    }

    private fun fromStringToByteArray(input: String): ByteArray? {
        return Base64.decode(input, Base64.NO_WRAP)
    }

    fun checkPin(savedPin: String, input: String): Boolean {
        val parts = savedPin.split(":")
        val salt = fromStringToByteArray(parts[0])
        val hash = fromStringToByteArray(parts[1])
        val inputhash = generateRandomHash(input, salt!!)
        return inputhash?.contentEquals(hash!!)!!
    }

    fun encrypt(input: String, savedPin: String): String {
        val key = savedPin
        val salt = generateRandomSalt()
        val iv = generateRandomIv()
        val ivSpec = IvParameterSpec(iv)

        val keyBytes = generateRandomHash(key, salt)
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(fromStringToByteArray(input))

        val saltstr = fromByteArrayToString(salt)
        val ivstr = fromByteArrayToString(iv)
        val encryptedstr = fromByteArrayToString(encrypted)

        d("hey", key)
        d("hey", "salt: " + salt.contentToString())
        d("hey", "salt: " + fromByteArrayToString(salt))
        d("hey", "iv: " + iv.contentToString())
        d("hey", "iv: " + fromByteArrayToString(iv))
        d("hey", "encrypt: " + encrypted?.contentToString()!!)
        d("hey", "encyptt: " + fromByteArrayToString(encrypted))

        d("heyy", "$saltstr:$ivstr:$encryptedstr")

        return "$saltstr:$ivstr:$encryptedstr"
    }

    fun decrypt(input: String, savedPin: String): String {
        val key = savedPin

        val parts = input.split(":")
        val salt = fromStringToByteArray(parts[0])
        val iv = fromStringToByteArray(parts[1])
        val encrypted = fromStringToByteArray(parts[2])
        val ivSpec = IvParameterSpec(iv)

        d("heyy", input)
        d("hey", key)
        d("hey", "salt: " + salt?.contentToString()!!)
        d("hey", "salt: " + fromByteArrayToString(salt))
        d("hey", "iv: " + iv?.contentToString()!!)
        d("hey", "iv: " + fromByteArrayToString(iv))
        d("hey", "encrypt: " + encrypted?.contentToString()!!)
        d("hey", "encyptt: " + fromByteArrayToString(encrypted))

        val keyBytes = generateRandomHash(key, salt)
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decrypted = cipher.doFinal(encrypted)
        d("heyy", fromByteArrayToString(decrypted)!!)
        val decryptedstr = fromByteArrayToString(decrypted)
        if (decryptedstr?.endsWith("==")!!){
            decryptedstr
        }
        return decryptedstr
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