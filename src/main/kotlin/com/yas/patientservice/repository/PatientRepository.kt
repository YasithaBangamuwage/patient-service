package com.yas.patientservice.repository

import com.yas.patientservice.model.Patient
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PatientRepository : CoroutineCrudRepository<Patient, Long> {

    fun findByNameContaining(name: String): Flow<Patient>

    fun findByGender(gender: Int): Flow<Patient>

    fun findByName(name : String) : Flow<Patient>



}