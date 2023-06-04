package com.yas.patientservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("patientservice.patient")
data class Patient(@Id val id: Long? = null, val name: String, val age: Int, val gender: Int)
