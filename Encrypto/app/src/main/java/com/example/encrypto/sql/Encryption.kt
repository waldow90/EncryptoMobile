package com.example.encrypto.sql

import java.math.BigInteger
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

        return toHex(salt) + ":" + toHex(hash!!)
    }

    fun checkPin(savedPin: String, input: String): Boolean {
        val parts = savedPin.split(":")
        val salt = fromHex(parts[0])
        val hash = fromHex(parts[1])
        val inputhash = generateRandomHash(input, salt)
        return inputhash?.contentEquals(hash)!!
    }

    fun encrypt(input: String, savedPin: String): String {
        val key = fromHex(savedPin.split(":")[1])
        val salt = generateRandomSalt()
        val iv = generateRandomIv()
        val ivSpec = IvParameterSpec(iv)
        val pbKeySpec = PBEKeySpec(toHex(key).toCharArray(), salt, iterations, hashSize)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBEwithHmacSHA512AndAES_256")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(input.toByteArray())

        return toHex(salt) + ":" + toHex(iv) + ":" + toHex(encrypted)
    }

    fun decrypt(input: String, savedPin: String): String {
        val key = fromHex(savedPin.split(":")[1])

        val parts = input.split(":")
        val salt = fromHex(parts[0])
        val iv = fromHex(parts[1])
        val encrypted = fromHex((parts[2]))

        val pbKeySpec = PBEKeySpec(toHex(key).toCharArray(), salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decrypted = cipher.doFinal(encrypted)
        return toHex(decrypted)
    }

    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        return if (paddingLength > 0) {
            String.format("%0" + paddingLength + "d", 0) + hex
        } else {
            hex
        }
    }

    private fun fromHex(hex: String): ByteArray {
        val bytes = ByteArray(hex.length / 2)
        for (i in bytes.indices) {
            bytes[i] = Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16).toByte()
        }
        return bytes
    }

    private fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(saltSize)
        random.nextBytes(salt)
        return salt
    }

    private fun generateRandomHash(password: String, salt: ByteArray): ByteArray? {
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, iterations, hashSize * 8)
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