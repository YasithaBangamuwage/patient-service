package com.yas.patientservice.service

import com.yas.patientservice.model.Patient
import com.yas.patientservice.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PatientService(private val patientRepository: PatientRepository) {

    suspend fun savePatient(patient: Patient): Patient? =
        patientRepository.findByName(patient.name).firstOrNull()
            ?.let { throw ResponseStatusException(HttpStatus.BAD_REQUEST) }
            ?: patientRepository.save(patient);

    suspend fun findAllPatients(): Flow<Patient> = patientRepository.findAll()

    suspend fun findById(id: Long): Patient? = patientRepository.findById(id)

    suspend fun deleteById(id: Long) {
        val foundPatient = patientRepository.findById(id)
        if (foundPatient == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else {
            patientRepository.deleteById(id)
        }
    }

    suspend fun updateById(id: Long, requestedPatient: Patient): Patient {

        val foundPatient = patientRepository.findById(id)

        return if (foundPatient == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else {
            patientRepository.save(requestedPatient.copy(id = foundPatient.id))
        }
    }

    suspend fun findByNameLike(name: String): Flow<Patient> =
        patientRepository.findByNameContaining(name)

}