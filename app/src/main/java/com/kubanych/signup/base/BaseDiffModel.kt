package com.kubanych.signup.base

interface BaseDiffModel {
    val id: String?
    override fun equals(other: Any?): Boolean
}